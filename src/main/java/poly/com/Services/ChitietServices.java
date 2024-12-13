package poly.com.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.com.Entity.Product;
import poly.com.Entity.Product_img;
import poly.com.Entity.Reviews;
import poly.com.Repository.ProductRepo;
import poly.com.Repository.Product_imgRepo;
import poly.com.Repository.ReviewRepo;

import java.util.Collections;
import java.util.List;

@Service
public class ChitietServices {

    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private Product_imgRepo productImageRepository;

    @Autowired
    private ReviewRepo reviewRepository;

    /**
     * Lấy sản phẩm theo ID
     *
     * @param id ID của sản phẩm
     * @return Sản phẩm tương ứng hoặc null nếu không tìm thấy
     */
    public Product findById(int id) {
        return productRepository.findById(id).orElse(null); // Trả về null nếu không tìm thấy sản phẩm
    }

    /**
     * Lấy hình ảnh của sản phẩm theo ID sản phẩm
     *
     * @param productId ID của sản phẩm
     * @return Danh sách hình ảnh của sản phẩm
     */
    public List<Product_img> findImagesByProductId(int productId) {
        return productImageRepository.findByProductId(productId); // Trả về danh sách hình ảnh
    }

    /**
     * Lấy đánh giá của sản phẩm theo ID sản phẩm
     *
     * @param productId ID của sản phẩm
     * @return Danh sách đánh giá của sản phẩm
     */
    public List<Reviews> findReviewsByProductId(int productId) {
        return reviewRepository.findByProductId(productId); // Trả về danh sách đánh giá
    }

    /**
     * Lấy sản phẩm liên quan theo ID danh mục
     *
     * @param categoryId ID của danh mục
     * @return Danh sách sản phẩm liên quan theo danh mục
     */
    public List<Product> findRelatedProducts(int categoryId) {
        return productRepository.findByCategoryId(categoryId); // Trả về danh sách sản phẩm liên quan
    }

    /**
     * Lấy sản phẩm liên quan ngẫu nhiên từ cùng một danh mục
     *
     * @param categoryId ID của danh mục
     * @return Danh sách sản phẩm ngẫu nhiên (tối đa 4 sản phẩm)
     */
    public List<Product> findRandomRelatedProducts(int categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId); // Lấy danh sách sản phẩm cùng danh mục
        Collections.shuffle(products); // Trộn danh sách sản phẩm
        return products.subList(0, Math.min(products.size(), 4)); // Trả về tối đa 4 sản phẩm ngẫu nhiên
    }
}
