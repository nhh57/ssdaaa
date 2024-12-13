package poly.com.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.com.Entity.User;
import poly.com.DAO.UserDAO;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserDAO userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "Login/Dangki";
    }

    @PostMapping
    public String registerUser(User user, String confirmPassword, Model model) {
        
        if (userService.findUserByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email đã được sử dụng.");
            return "Login/Dangki";
        }

        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu và xác nhận mật khẩu không khớp.");
            return "Login/Dangki";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole(false); 
        }

        userService.registerUser(user);
        return "redirect:/login";
    }
}
