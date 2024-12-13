package poly.com.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import poly.com.Entity.Product_img;

public interface Product_imgRepo extends JpaRepository<Product_img, Integer> {
	List<Product_img> findByProductId(int productId);
}
