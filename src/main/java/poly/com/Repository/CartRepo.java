package poly.com.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import poly.com.Entity.Cart;
import poly.com.Entity.Product;
import poly.com.Entity.User;

public interface CartRepo extends JpaRepository<Cart, Integer> {

    /**
     * Tìm sản phẩm trong giỏ hàng dựa trên người dùng và sản phẩm.
     * @param user đối tượng người dùng
     * @param product đối tượng sản phẩm
     * @return Cart nếu tồn tại sản phẩm trong giỏ của người dùng
     */
    Cart findByUserAndProduct(User user, Product product);

    /**
     * Tìm tất cả các sản phẩm trong giỏ hàng của một người dùng dựa trên ID người dùng.
     * @param userId ID của người dùng
     * @return Danh sách Cart
     */
    List<Cart> findByUserId(int userId);

    /**
     * Đếm tổng số lượng sản phẩm trong giỏ hàng của người dùng.
     * @param userId ID của người dùng
     * @return Tổng số lượng sản phẩm
     */
    @Query("SELECT COALESCE(SUM(c.quantity), 0) FROM Cart c WHERE c.user.id = :userId")
    Long countByUserId(@Param("userId") Integer userId);


    void deleteByUserId(int userId);
}
