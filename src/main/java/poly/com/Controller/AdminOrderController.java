package poly.com.Controller;

import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import poly.com.Entity.Order;
import poly.com.Entity.Order_Detail;
import poly.com.Services.OrderDetailService;
import poly.com.Services.OrderService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {
    @Resource
    private OrderService orderService;



    @GetMapping("/orders")
    public String orders(Model model) {
        List<Order> data = orderService.getOrders();
        model.addAttribute("orders", data);
        return "Admin/OrderMana";
    }


}
