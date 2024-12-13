package poly.com.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.com.Entity.User;
import poly.com.Repository.UserRepo;



@Service
public class AdminUserServices {

    @Autowired
    private UserRepo userRepository;
    
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
    
    public void saveUser(User student) {
        userRepository.save(student);
    }
    
    public void deleteUserById(int id) {
    	userRepository.deleteById(id);
    }
    
    public User getUserById(int id) {
        Optional<User> optional = userRepository.findById(id);
        return optional.orElse(null);
    }
    
    public List<User> searchUsersByKeyword(String keyword) {
        return userRepository.findByHotenContainingOrEmailContaining(keyword, keyword);
    }



}
