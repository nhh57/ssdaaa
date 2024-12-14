package poly.com.Controller.api;

import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poly.com.Entity.Order_Detail;
import poly.com.Services.OrderDetailService;
import poly.com.data.OrderDetailDTO;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminApiOrderController {

    @Resource
    private OrderDetailService orderDetailService;

    @GetMapping("/get-order-detail/{id}")
    public ResponseEntity<List<OrderDetailDTO>> getOrderDetails(@PathVariable int id) {
        List<Order_Detail> orderDetails = orderDetailService.findOrderDetailsByOrderId(id);
        if (!orderDetails.isEmpty()) {
            List<OrderDetailDTO> dtos = orderDetails.stream()
                    .map(orderDetail -> new OrderDetailDTO(
                            orderDetail.getId(),
                            orderDetail.getProduct().getId(),
                            orderDetail.getProduct().getName(),
                            orderDetail.getUnit_price(),
                            orderDetail.getQuantity(),
                            orderDetail.getOrder().getId()
                    ))
                    .toList();
            return ResponseEntity.ok(dtos);
        }
        return ResponseEntity.notFound().build();
    }

}
