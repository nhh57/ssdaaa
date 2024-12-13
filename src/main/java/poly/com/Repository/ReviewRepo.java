package poly.com.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import poly.com.Entity.Reviews;

public interface ReviewRepo extends JpaRepository<Reviews, Integer> {
    List<Reviews> findByProductId(int productId);
}
