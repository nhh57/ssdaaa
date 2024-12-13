package poly.com.Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Order")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ID_User", referencedColumnName = "ID_User")
    @Column(name="ID_User")
    private int user;

    @Column(name = "order_date")
    private LocalDate order_date;

    @Column(name = "total_amount")
    private double total_amount;

    @Column(name = "status")
    private String status;

//    // Liên kết với Order_Detail
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Order_Detail> orderDetails;
}
