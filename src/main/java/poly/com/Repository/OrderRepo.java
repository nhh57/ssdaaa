package poly.com.Repository;

import org.springframework.data.repository.CrudRepository;
import poly.com.Entity.Order;

import java.util.List;

public interface OrderRepo extends CrudRepository<Order, Long> {

    List<Order> getOrdersByUser(int userId);
}
