package poly.com.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDataModel {

    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal discount;
    private int quantity;
    private String image;


}
