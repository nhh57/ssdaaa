package poly.com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.com.Entity.Brand;
@Repository
public interface BrandRepo extends JpaRepository<Brand, Integer> {
}
