package poly.com.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import poly.com.Entity.Cart;
import poly.com.Services.CartService;
import poly.com.Services.UserServices;

@Controller
public class HomeController {

    @Autowired
    private UserServices userService;
    
    @Autowired
    private CartService cartService;


    @GetMapping("/index")
    public String index(Model model) {
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

        return "Home/index"; // Trả về trang index
    }
    
    private Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName(); // Lấy email từ Authentication
            return userService.getIdUserByEmail(email); // Lấy id_user từ UserServices
        }
        return null;
    }

}
