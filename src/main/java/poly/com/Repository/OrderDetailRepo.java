package poly.com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.com.Entity.Order_Detail;

import java.util.List;

public interface OrderDetailRepo extends JpaRepository<Order_Detail, Integer> {

//    Order_Detail findByOrder_Id(int orderId);

    List<Order_Detail> findByOrder_Id(int orderId);
}
