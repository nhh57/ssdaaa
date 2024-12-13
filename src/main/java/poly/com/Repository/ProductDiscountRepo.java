package poly.com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import poly.com.Entity.ProductDiscount;

import java.util.List;

public interface ProductDiscountRepo extends JpaRepository<ProductDiscount, Long> {

    @Query(value = "SELECT p.id, p.name, p.price, "
            + "CASE "
            + "    WHEN d.start_date <= GETDATE() AND d.end_date >= GETDATE() THEN d.discount_percentage "
            + "    ELSE 0 "
            + "END AS discount_percentage, "
            + "p.description, p.stock_quantity, p.image_url "
            + "FROM Product p "
            + "LEFT JOIN Product_Discount pd ON p.id = pd.product_id "
            + "LEFT JOIN Discount d ON pd.discount_id = d.id "
            + "WHERE ((?1 <> '' AND p.name LIKE CONCAT('%', ?1, '%')) OR ?1 = '') "
            + "AND (?2 = 0 OR p.category_id = ?2) "
            + "AND ((?3 = 0.0 AND ?4 = 0.0) OR (p.price BETWEEN ?3 AND ?4))",
            nativeQuery = true)
    List<Object[]> findByProducts(String name, Integer categoryId, Double minPrice, Double maxPrice);
}
