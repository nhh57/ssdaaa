package poly.com.Services;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import poly.com.Entity.Cart;
import poly.com.Entity.Order;
import poly.com.Entity.Order_Detail;
import poly.com.Repository.OrderRepo;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class OrderService {
    @Resource
    CartService cartService;

    @Resource
    OrderRepo orderRepo;

    @Resource
    OrderDetailService orderDetailService;

    @Transactional
    public void save(Integer userId) {
        List<Cart> carts = null;
        Long cartItemCount = 0L; // Biến đếm số lượng sản phẩm trong giỏ hàng
        double totalAmount = 0.0; // Biến lưu tổng số tiền

        if (userId != null) {
            carts = cartService.getAllProductsByUserId(userId); // Lấy sản phẩm của người dùng
            cartItemCount = cartService.countItemsInCart(userId); // Đếm sản phẩm trong giỏ hàng

            // Tính tổng số tiền giỏ hàng
            for (Cart cartItem : carts) {
                totalAmount += cartItem.getQuantity() * cartItem.getUnit_price(); // Cộng dồn giá trị sản phẩm
            }
        } else {
            carts = List.of(); // Nếu không có người dùng, tạo danh sách rỗng
        }
        Order order = Order.builder()
                .user(userId)
                .status("aa")
                .order_date(LocalDate.now())
                .total_amount(totalAmount).build();
        orderRepo.save(order);

//        log.info("save carts id:{}", carts);
        carts.forEach(cart -> {
            Order_Detail orderDetail = Order_Detail.builder()
                    .order(order)
                    .product(cart.getProduct())
                    .quantity(cart.getProduct().getQuantity())
                    .unit_price(cart.getProduct().getPrice()).build();
            orderDetailService.saveOrderDetail(orderDetail);
        });

        int userID = carts.get(0).getUser().getId();
        cartService.deleteCardByUserId(userID);
    }


    public List<Order> getAllOrdersByUserId(int userId) {
        return  orderRepo.getOrdersByUser(userId);
    }

public List<Order> getOrders(){
        return (List<Order>) orderRepo.findAll();
}

}
