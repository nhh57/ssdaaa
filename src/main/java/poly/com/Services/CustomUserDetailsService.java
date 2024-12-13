package poly.com.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import poly.com.DAO.UserDAO;
import poly.com.Entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDAO.findUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Email không tồn tại");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .disabled(false) 
                .accountExpired(false) 
                .accountLocked(false) 
                .credentialsExpired(false)
                .build();
    }
}
