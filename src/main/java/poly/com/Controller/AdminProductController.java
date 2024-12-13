package poly.com.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import poly.com.Entity.Brand;
import poly.com.Entity.Category;
import poly.com.Entity.Product;
import poly.com.Services.AdminProductService;
import poly.com.Services.BrandService;
import poly.com.Services.CategoryService;

@Controller
@RequestMapping("/admin")
public class AdminProductController {

    @Autowired
    private AdminProductService proService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    // Hiển thị danh sách sản phẩm
    @GetMapping("/products")
    public String viewProduct(Model model) {
        List<Product> products = proService.getAllProduct();
        List<Category> categories = categoryService.getAllCategories();
        List<Brand> brands = brandService.getAllBrands();
        
        model.addAttribute("products", products); // Đưa danh sách sản phẩm vào model
        model.addAttribute("categories", categories); // Đưa danh mục vào model
        model.addAttribute("brands", brands); // Đưa hãng vào model
        
        return "Admin/ProductMana"; // Trả về template quản lý sản phẩm
    }

    // Hiển thị chi tiết sản phẩm để chỉnh sửa
    @GetMapping("/editProduct/{id}")
    public String showEditProductPage(@PathVariable("id") int id, Model model) {
        Product product = proService.getProductById(id); // Lấy thông tin sản phẩm theo ID
        model.addAttribute("product", product); // Đưa thông tin sản phẩm vào model
        model.addAttribute("categories", categoryService.getAllCategories()); // Đưa danh mục vào model
        model.addAttribute("brands", brandService.getAllBrands()); // Đưa hãng vào model
        
        return "Admin/EditProduct"; // Trả về template chỉnh sửa sản phẩm
    }

    // Lưu hoặc cập nhật sản phẩm
    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") Product product) {
        proService.saveProduct(product); // Lưu hoặc cập nhật sản phẩm
        return "redirect:/admin/products"; // Chuyển hướng lại trang quản lý sản phẩm
    }
    
 // Sử dụng phương thức DELETE để xóa sản phẩm
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        proService.deleteProductById(id); // Xóa sản phẩm theo ID
        return "redirect:/admin/products"; // Chuyển hướng lại trang quản lý sản phẩm
    }

    
    // Thêm sản phẩm mới
    @PostMapping("/addProduct")
    public String addProduct(@RequestParam("name") String name,
                             @RequestParam("price") double price,
                             @RequestParam("quantity") int quantity,
                             @RequestParam("description") String description,
                             @RequestParam("category") int categoryId,
                             @RequestParam("brand") int brandId,
                             @RequestParam("image") MultipartFile imageFile) {
        try {
            // Lưu hình ảnh lên server
            String imagePath = saveImage(imageFile); // Lưu hình ảnh và nhận đường dẫn
            
            // Tạo đối tượng sản phẩm
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setDescription(description);
            product.setCategory(categoryService.getCategoryById(categoryId)); // Thiết lập danh mục
            product.setBrand(brandService.getBrandById(brandId)); // Thiết lập hãng
            product.setImage(imagePath); // Lưu đường dẫn hình ảnh

            // Lưu sản phẩm vào cơ sở dữ liệu
            proService.saveProduct(product);
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log nếu có lỗi xảy ra
        }
        return "redirect:/admin/products"; // Chuyển hướng lại trang quản lý sản phẩm
    }

    // Phương thức lưu hình ảnh
    private String saveImage(MultipartFile imageFile) throws IOException {
        // Đường dẫn tương đối tới thư mục static/IMG/Img_Pro_Main
        String uploadDir = "src/main/resources/static/IMG/Img_Pro_Main/";

        // Đảm bảo thư mục tồn tại
        Files.createDirectories(Paths.get(uploadDir));

        // Lấy tên file gốc của hình ảnh
        String imageName = imageFile.getOriginalFilename();
        
        // Tạo đường dẫn đầy đủ để lưu ảnh
        Path imagePath = Paths.get(uploadDir + imageName);

        // Lưu tệp hình ảnh vào thư mục đã chỉ định
        Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

        // Trả về đường dẫn để truy cập ảnh qua trình duyệt
        return "/IMG/Img_Pro_Main/" + imageName;  // Trả về đường dẫn tương đối
    }

}
