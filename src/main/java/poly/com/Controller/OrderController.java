package poly.com.Controller;

import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import poly.com.Entity.Order;
import poly.com.Services.OrderService;
import poly.com.Services.UserServices;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderService orderService;

    @Resource
    UserServices userServices;

    @GetMapping("")
    public String viewOrder(Model model) {
        int userId = getCurrentUserId();
        List<Order> orders = orderService.getAllOrdersByUserId(userId);

        model.addAttribute("orders", orders);
        return "User/order";
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
