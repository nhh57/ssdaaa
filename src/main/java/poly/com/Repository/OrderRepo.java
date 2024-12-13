package poly.com.Repository;

import org.springframework.data.repository.CrudRepository;
import poly.com.Entity.Order;

public interface OrderRepo extends CrudRepository<Order, Long> {


}
