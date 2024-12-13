package poly.com.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Reviews")
public class Reviews implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "product_id")
    private int productId;
    
    @Column(name = "ID_user")
    private String user_id;
    
    @Column(name = "rating")
    private String rate;
    
    @Column(name = "review_date")
    private LocalDateTime review_date;
}

