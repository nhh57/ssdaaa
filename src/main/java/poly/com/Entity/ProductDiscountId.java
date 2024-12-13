package poly.com.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ProductDiscountId implements java.io.Serializable {
    private static final long serialVersionUID = -2469192956554755544L;
    @NotNull
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @NotNull
    @Column(name = "discount_id", nullable = false)
    private Integer discountId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductDiscountId entity = (ProductDiscountId) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.discountId, entity.discountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, discountId);
    }

}