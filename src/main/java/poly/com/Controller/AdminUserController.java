package poly.com.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import poly.com.Services.AdminUserServices;
import poly.com.Entity.User;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private AdminUserServices userService;

    // Hiển thị danh sách người dùng và form thêm mới
    @GetMapping("/users")
    public String viewUsers(Model model) {
        List<User> users = userService.getAllUser(); // Lấy danh sách người dùng
        model.addAttribute("users", users); // Đưa danh sách người dùng vào model
        model.addAttribute("user", new User()); // Đưa đối tượng user rỗng vào form
        return "Admin/UserMana"; // Trả về template Thymeleaf "UserMana.html"
    }

    // Lưu người dùng (thêm mới hoặc cập nhật)
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user); // Lưu hoặc cập nhật người dùng
        return "redirect:/admin/users"; // Chuyển hướng lại trang quản lý người dùng
    }

    // Hiển thị form sửa người dùng
    @GetMapping("/editUser/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        User user = userService.getUserById(id); // Lấy người dùng theo ID
        model.addAttribute("user", user); // Đưa đối tượng người dùng vào form để chỉnh sửa
        model.addAttribute("users", userService.getAllUser()); // Đưa danh sách người dùng vào model
        return "Admin/UserMana"; // Trả về trang quản lý người dùng với form sửa
    }

    // Xóa người dùng
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUserById(id); // Xóa người dùng theo ID
        return "redirect:/admin/users"; // Chuyển hướng lại trang quản lý người dùng
    }
    
    @GetMapping("/searchUser")
    public String searchUsers(@RequestParam("keyword") String keyword, Model model) {
        List<User> users = userService.searchUsersByKeyword(keyword);
        model.addAttribute("users", users); 
        model.addAttribute("user", new User()); 
        return "Admin/UserMana"; 
    }

}
