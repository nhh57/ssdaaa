package poly.com.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetailDTO {
    private int id;
    private int productId;
    private String productName;
    private double unitPrice;
    private int quantity;
    private int orderId;
}
