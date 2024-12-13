package poly.com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.com.Entity.Order_Detail;

public interface OrderDetailRepo extends JpaRepository<Order_Detail, Integer> {
}
