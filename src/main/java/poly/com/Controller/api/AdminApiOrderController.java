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

@RestController
@RequestMapping("/admin")
public class AdminApiOrderController {

    @Resource
    private OrderDetailService orderDetailService;

    @GetMapping("/get-order-detail/{id}")
    public ResponseEntity<OrderDetailDTO> getOrderDetail(@PathVariable int id) {
        Order_Detail orderDetail = orderDetailService.findOrderDetailByOrderId(id);
        if (orderDetail != null) {
            OrderDetailDTO dto = new OrderDetailDTO(
                    orderDetail.getId(),
                    orderDetail.getProduct().getId(),
                    orderDetail.getProduct().getName(),
                    orderDetail.getUnit_price(),
                    orderDetail.getQuantity(),
                    orderDetail.getOrder().getId()
            );
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }
}
