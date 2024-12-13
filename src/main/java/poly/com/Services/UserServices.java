package poly.com.Services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.com.Entity.User;
import poly.com.Repository.UserRepo;

@Service
public class UserServices {

    @Autowired
    private UserRepo userRepository;

    public String getFullnameByUsername(String username) {
        User user = userRepository.findByEmail(username);
        return user != null ? user.getHoten() : null;
    }

    public String getRoleByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ? (user.getRole() ? "ROLE_ADMIN" : "ROLE_USER") : null; // Giả sử bạn có 2 vai trò: admin và user
    }

    public String getRoleByUsername(String username) {
        User user = userRepository.findByEmail(username);
        return user != null ? (user.getRole() ? "ROLE_ADMIN" : "ROLE_USER") : null; // 
    }
    
    public Integer getIdUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ? user.getId() : null; // Trả về id kiểu Integer
    }
    
}
