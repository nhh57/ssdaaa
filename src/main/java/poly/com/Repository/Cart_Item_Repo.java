package poly.com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.com.Entity.Cart_Item;

@Repository
public interface Cart_Item_Repo extends JpaRepository<Cart_Item, Integer> {
}
