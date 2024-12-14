package poly.com.Controller.api;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import poly.com.Services.OrderService;
import poly.com.Services.UserServices;

@RestController
@Slf4j
public class CheckoutController {
    @Resource
    UserServices userServices;

    @Resource
    OrderService orderService;

    @PostMapping("/checkout")
    public String SaveOrder() {
        try {
            int userId = getCurrentUserId();
            log.info("userId::", userId);
            orderService.save(userId);
            return "redirect:/order";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/order";
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
}
