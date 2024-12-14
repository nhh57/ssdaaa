package poly.com.Services;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.com.Entity.Order_Detail;
import poly.com.Repository.OrderDetailRepo;

import java.util.List;

@Service
public class OrderDetailService {
    @Resource
    OrderDetailRepo repo;

    @Transactional
    public void saveOrderDetail(Order_Detail orderDetail) {
        repo.save(orderDetail);
    }


    public List<Order_Detail> findOrderDetailsByOrderId(int orderId) {
        return repo.findByOrder_Id(orderId);
    }
}
