package poly.com.Services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.com.Entity.Product;
import poly.com.Repository.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepository;

    /**
     * Tìm kiếm sản phẩm dựa trên từ khóa, danh mục, khoảng giá.
     * Nếu các tham số lọc không được cung cấp, trả về tất cả sản phẩm.
     * 
     * @param search từ khóa tìm kiếm (có thể null)
     * @param categoryId ID danh mục (có thể null)
     * @param minPrice giá thấp nhất (có thể null)
     * @param maxPrice giá cao nhất (có thể null)
     * @return danh sách sản phẩm phù hợp với bộ lọc
     */
    public List<Product> searchProducts(String search, Integer categoryId, Double minPrice, Double maxPrice) {
        if (search == null && categoryId == null && minPrice == null && maxPrice == null) {
            return productRepository.findAll();  // Nếu không có điều kiện lọc, trả về tất cả sản phẩm
        }
        return productRepository.searchProducts(search, categoryId, minPrice, maxPrice);
    }

    /**
     * Lấy tất cả sản phẩm từ cơ sở dữ liệu.
     * 
     * @return danh sách tất cả sản phẩm
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Lấy chi tiết sản phẩm dựa trên ID.
     * 
     * @param id ID của sản phẩm
     * @return sản phẩm có ID tương ứng, nếu không tìm thấy trả về null
     */
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }
}
