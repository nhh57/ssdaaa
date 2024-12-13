package poly.com.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.com.Entity.User;
import poly.com.Repository.UserRepo;

@Service
public class UserDAO {

    @Autowired
    private UserRepo userRepository;

    public User registerUser(User user) {
        if (findUserByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }
        return userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
