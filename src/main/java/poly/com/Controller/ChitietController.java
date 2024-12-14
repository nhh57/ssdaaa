package poly.com.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import poly.com.Entity.Cart;
import poly.com.Entity.Product;
import poly.com.Entity.Product_img;
import poly.com.Entity.Reviews;
import poly.com.Services.CartService;
import poly.com.Services.ChitietServices;
import poly.com.Services.UserServices;

import java.util.List;

@Controller
public class ChitietController {

    @Autowired
    private ChitietServices chitietService;
    @Autowired
    private UserServices userService;
    
    @Autowired
    private CartService cartService;

    /**
     * Hiển thị chi tiết sản phẩm theo ID
     *
     * @param productId ID của sản phẩm
     * @param model     Đối tượng Model để truyền dữ liệu đến view
     * @return Tên của view sản phẩm chi tiết hoặc chuyển hướng đến trang 404
     */
    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable("id") int productId, Model model) {
        // Lấy sản phẩm theo ID
        Product product = chitietService.findById(productId);
        if (product == null) {
            // Xử lý khi không tìm thấy sản phẩm
            return "redirect:/404"; // Chuyển hướng đến trang lỗi 404 hoặc trang nào đó
        }

        model.addAttribute("product", product);

        // Lấy hình ảnh của sản phẩm
        List<Product_img> images = chitietService.findImagesByProductId(productId);
        model.addAttribute("images", images);

        // Lấy đánh giá của sản phẩm
        List<Reviews> reviews = chitietService.findReviewsByProductId(productId);
        model.addAttribute("reviews", reviews);

        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        boolean isLoggedIn = checkIfUserIsLoggedIn();
        model.addAttribute("isLoggedIn", isLoggedIn);

        // Lấy sản phẩm liên quan ngẫu nhiên theo category
        List<Product> relatedProducts = chitietService.findRandomRelatedProducts(product.getCategory().getId());
        model.addAttribute("relatedProducts", relatedProducts);
        
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

        return "Product/ChiTiet"; // Trả về view cho trang chi tiết sản phẩm
    }
    
    private Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName(); // Lấy email từ Authentication
            return userService.getIdUserByEmail(email); // Lấy id_user từ UserServices
        }
        return null;
    }

    /**
     * Kiểm tra xem người dùng đã đăng nhập hay chưa
     *
     * @return true nếu người dùng đã đăng nhập, false nếu không
     */
    private boolean checkIfUserIsLoggedIn() {
        // Cần triển khai phương thức này để kiểm tra trạng thái người dùng từ session hoặc security context
        // Ví dụ:
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // return auth != null && !(auth instanceof AnonymousAuthenticationToken);
        
        return false; // Thay đổi logic để kiểm tra trạng thái đăng nhập
    }
}
