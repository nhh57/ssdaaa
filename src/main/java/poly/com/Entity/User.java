package poly.com.Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "[User]")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_User")
    private int id;

    @Column(name = "Fullname")
    private String hoten;

    @Column(name = "Email")
    private String email;

    @Column(name = "Adress")
    private String dchi;

    @Column(name = "Phone_number")
    private String sdt;

    @Column(name = "img")
    private String anh;

    @Column(name = "Brth_day")
    private LocalDate ng_sinh;

    @Column(name = "Role")
    private Boolean role;

    @Column(name = "Password")
    private String Password;
    
    @Column(name = "avatar")
    private String avatar;
    
    @Column(name = "is_google_login")
    private Boolean is_google_login;

    // Liên kết với Order
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
}
