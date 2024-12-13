package poly.com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.com.Entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
