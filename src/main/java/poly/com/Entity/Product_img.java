package poly.com.Entity;

import java.io.Serializable;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Pro_img")
public class Product_img implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Đây là product_id để liên kết với bảng Product
    @Column(name = "product_id", nullable = false)
    private int productId;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    // Thiết lập mối quan hệ với Product
    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product; // Trường này không được cập nhật, chỉ để lấy thông tin
}
