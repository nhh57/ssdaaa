package poly.com.Controller;

import java.util.List;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import poly.com.Entity.ProductDiscount;
import poly.com.Services.*;
import poly.com.Entity.Cart;

@Controller
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserServices userService;

    @Autowired
    private CartService cartService;

    @Resource
    ProductDiscountService productDiscountService;

    // Tìm kiếm và lọc sản phẩm
    @GetMapping("/product") // Đổi từ "/product" sang "/products"
    public String viewProducts(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "category", required = false) Integer categoryId,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            Model model) {

//       log.info("dataa::{}", productDiscountService.getProductDiscounts());

        model.addAttribute("products", productDiscountService.getProductDiscountss(search, categoryId, minPrice, maxPrice));

        // Lấy danh sách sản phẩm sau khi áp dụng bộ lọc từ ProductService
//        model.addAttribute("products", productService.searchProducts(search, categoryId, minPrice, maxPrice));

        // Lấy danh sách tất cả danh mục
        model.addAttribute("categories", categoryService.getAllCategories());

        // Kiểm tra nếu không có sản phẩm nào phù hợp với tìm kiếm
        if (productService.searchProducts(search, categoryId, minPrice, maxPrice).isEmpty()) {
            model.addAttribute("message", "Không tìm thấy sản phẩm phù hợp.");
        }

        // Lấy đối tượng Authentication từ SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra nếu người dùng đã đăng nhập bằng OAuth2
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            // Lấy email và fullname từ OAuth2User (ví dụ Google login)
            String email = oAuth2User.getAttribute("email");
            String fullname = oAuth2User.getAttribute("name");

            // Thêm email và fullname vào model
            model.addAttribute("username", email);
            model.addAttribute("fullname", fullname);

            // Lấy role từ userService (nếu cần)
            String role = userService.getRoleByEmail(email);
            model.addAttribute("role", role);

        } else {
            // Trường hợp đăng nhập bình thường (không phải OAuth2)
            String username = authentication != null ? authentication.getName() : null;

            if (username != null) {
                // Lấy fullname và role từ service bằng username (email)
                String fullname = userService.getFullnameByUsername(username);
                String role = userService.getRoleByUsername(username);

                // Thêm thông tin vào model
                model.addAttribute("username", username);
                model.addAttribute("fullname", fullname);
                model.addAttribute("role", role);
            } else {
                // Nếu không có người dùng nào đăng nhập
                model.addAttribute("username", null);
                model.addAttribute("fullname", null);
                model.addAttribute("role", null);
            }

            Integer id_user = getCurrentUserId(); // Lấy id_user của người dùng hiện tại
            List<Cart> carts;
            Long cartItemCount = 0L; // Biến đếm số lượng sản phẩm trong giỏ hàng

            if (id_user != null) {
                carts = cartService.getAllProductsByUserId(id_user); // Lấy sản phẩm của người dùng
                cartItemCount = cartService.countItemsInCart(id_user); // Đếm sản phẩm trong giỏ hàng
            } else {
                carts = List.of(); // Nếu không có người dùng, tạo danh sách rỗng
            }

            model.addAttribute("carts", carts); // Đưa danh sách sản phẩm vào model
            model.addAttribute("cartItemCount", cartItemCount); // Đưa số lượng sản phẩm vào model
        }

        return "Product/SanPham";  // Đảm bảo đường dẫn trỏ tới trang sản phẩm đúng
    }

    private Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName(); // Lấy email từ Authentication
            return userService.getIdUserByEmail(email); // Lấy id_user từ UserServices
        }
        return null;
    }

    // Hiển thị chi tiết sản phẩm (đổi đường dẫn để tránh xung đột)
    @GetMapping("/product-detail/{id}") // Đổi từ "/product/{id}" sang "/product-detail/{id}"
    public String viewProductDetail(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "Product/product-detail";  // Đảm bảo đường dẫn tới trang chi tiết sản phẩm
    }



}
