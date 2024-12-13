package poly.com.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files; // Thư viện để thao tác với hệ thống tệp
import java.nio.file.Path; // Thư viện cho đường dẫn
import java.nio.file.Paths; // Thư viện để tạo đường dẫn
import java.io.IOException; // Thư viện xử lý ngoại lệ cho I/O
import java.util.List;
import java.util.Optional;

import poly.com.Entity.Product;
import poly.com.Repository.ProductRepo;



@Service
public class AdminProductService {

    @Autowired
    private ProductRepo proRepo;
    
    
    
    public List<Product> getAllProduct() {
        return proRepo.findAll();
    }
    
    public void saveProduct(Product pro) {
        proRepo.save(pro);
    }
    
    public void deleteProductById(int id) {
        // Tìm sản phẩm theo ID
        Product product = proRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại")); // Kiểm tra sản phẩm

        // Lấy đường dẫn hình ảnh
        String imagePath = product.getImage(); // Giả sử bạn có phương thức getImagePath() để lấy đường dẫn hình ảnh

        // Xóa hình ảnh khỏi hệ thống tệp
        try {
            Path pathToDelete = Paths.get(imagePath);
            if (Files.exists(pathToDelete)) {
                Files.delete(pathToDelete); // Xóa tệp hình ảnh
            }
        } catch (IOException e) {
            e.printStackTrace(); // Xử lý lỗi nếu cần
        }

        // Xóa sản phẩm khỏi cơ sở dữ liệu
        proRepo.deleteById(id);
    }
    
    public Product getProductById(int id) {
        Optional<Product> optional = proRepo.findById(id);
        return optional.orElse(null);
    }
    



}
