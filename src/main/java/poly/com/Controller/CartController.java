package poly.com.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import poly.com.Entity.Cart;
import poly.com.Services.CartService;
import poly.com.Services.UserServices;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserServices userServices;

    // Hiển thị trang giỏ hàng của người dùng
    @GetMapping("/cart")
    public String viewProduct(Model model) {
        Integer id_user = getCurrentUserId(); // Lấy id_user của người dùng hiện tại
        List<Cart> carts;
        Long cartItemCount = 0L; // Biến đếm số lượng sản phẩm trong giỏ hàng
        double totalAmount = 0.0; // Biến lưu tổng số tiền

        if (id_user != null) {
            carts = cartService.getAllProductsByUserId(id_user); // Lấy sản phẩm của người dùng
            cartItemCount = cartService.countItemsInCart(id_user); // Đếm sản phẩm trong giỏ hàng

            // Tính tổng số tiền giỏ hàng
            for (Cart cartItem : carts) {
                totalAmount += cartItem.getQuantity() * cartItem.getUnit_price(); // Cộng dồn giá trị sản phẩm
            }
        } else {
            carts = List.of(); // Nếu không có người dùng, tạo danh sách rỗng
        }

        model.addAttribute("carts", carts); // Đưa danh sách sản phẩm vào model
        model.addAttribute("cartItemCount", cartItemCount); // Đưa số lượng sản phẩm vào model
        model.addAttribute("totalAmount", totalAmount); // Đưa tổng số tiền vào model
        return "Product/Cart"; // Trả về template quản lý sản phẩm
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("productId") int productId,
                            @RequestParam("quantity") int quantity,
                            @RequestParam("unit_price") double unitPrice,
                            RedirectAttributes redirectAttributes) {
        Integer id_user = getCurrentUserId(); // Lấy id_user của người dùng hiện tại

        if (id_user != null) {
            cartService.addToCart(id_user, productId, quantity, unitPrice); // Thêm sản phẩm vào giỏ
            redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng.");
        }

        return "redirect:/product/" + productId; // Điều hướng về trang chi tiết sản phẩm
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @GetMapping("/deleteCart/{id}")
    public String deleteCart(@PathVariable("id") int id) {
        cartService.deleteCartById(id); // Xóa sản phẩm theo ID
        return "redirect:/cart"; // Chuyển hướng lại trang quản lý sản phẩm
    }

    // Lấy id của người dùng hiện tại từ Authentication
    private Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName(); // Lấy email từ Authentication
            return userServices.getIdUserByEmail(email); // Lấy id_user từ UserServices
        }
        return null;
    }
    
    @PostMapping("/update-cart")
    @ResponseBody
    public String updateCartQuantity(@RequestParam("cartItemId") Integer cartItemId, 
                                     @RequestParam("quantity") int quantity) {
        // Thực hiện cập nhật số lượng sản phẩm trong giỏ hàng
        cartService.updateCartItem(cartItemId, quantity);

        // Tính lại tổng số tiền giỏ hàng
        Integer id_user = getCurrentUserId();
        List<Cart> carts = cartService.getAllProductsByUserId(id_user);
        double totalAmount = 0.0;
        for (Cart cartItem : carts) {
            totalAmount += cartItem.getQuantity() * cartItem.getUnit_price();
        }

        // Trả về tổng số tiền dưới dạng JSON
        return "{\"totalAmount\": \"" + totalAmount + "\"}";
    }
}
