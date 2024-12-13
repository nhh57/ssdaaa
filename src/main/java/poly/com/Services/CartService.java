package poly.com.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import poly.com.Entity.Cart;
import poly.com.Entity.Product;
import poly.com.Entity.User;
import poly.com.Repository.CartRepo;
import poly.com.Repository.ProductRepo;
import poly.com.Repository.UserRepo;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private ProductRepo productRepository;

    /**
     * Thêm sản phẩm vào giỏ hàng của người dùng
     * Nếu sản phẩm đã có trong giỏ, cập nhật số lượng
     *
     * @param id_user     ID của người dùng
     * @param product_id  ID của sản phẩm
     * @param quantity    Số lượng sản phẩm
     * @param unit_price  Đơn giá của sản phẩm
     */
    @Transactional
    public void addToCart(int id_user, int product_id, int quantity, double unit_price) {
        // Tìm kiếm User và Product theo ID
        User user = userRepository.findById(id_user).orElseThrow(() -> 
            new IllegalArgumentException("User not found with id: " + id_user));
        Product product = productRepository.findById(product_id).orElseThrow(() -> 
            new IllegalArgumentException("Product not found with id: " + product_id));

        // Tìm giỏ hàng của người dùng và sản phẩm
        Cart cart = cartRepository.findByUserAndProduct(user, product);

        if (cart == null) {
            // Nếu không tồn tại, tạo mới
            cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(quantity);
            cart.setUnit_price(unit_price);
            cart.setCreated_date(LocalDate.now());
            cart.setStatus("Pending");
            cartRepository.save(cart);
        } else {
            // Nếu tồn tại, cập nhật số lượng sản phẩm
            cart.setQuantity(cart.getQuantity() + quantity);
            cartRepository.save(cart);
        }
    }

    /**
     * Lấy tất cả sản phẩm trong giỏ hàng theo ID người dùng
     *
     * @param id_user ID của người dùng
     * @return Danh sách các sản phẩm trong giỏ hàng
     */
    public List<Cart> getAllProductsByUserId(int id_user) {
        return cartRepository.findByUserId(id_user);
    }

    /**
     * Xóa sản phẩm trong giỏ hàng theo ID giỏ hàng
     *
     * @param id ID của giỏ hàng
     */
    public void deleteCartById(int id) {
        cartRepository.deleteById(id);
    }
    @Transactional
    public void deleteCardByUserId(int userId){
        cartRepository.deleteByUserId(userId);
    }

    /**
     * Đếm số lượng sản phẩm trong giỏ hàng theo ID người dùng
     *
     * @param userId ID của người dùng
     * @return Số lượng sản phẩm trong giỏ hàng
     */
    public Long countItemsInCart(Integer userId) {
        return cartRepository.countByUserId(userId);
    }

    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng
     *
     * @param cartItemId ID của sản phẩm trong giỏ hàng
     * @param quantity   Số lượng mới
     */
    @Transactional
    public void updateCartItem(Integer cartItemId, int quantity) {
        Cart cartItem = cartRepository.findById(cartItemId)
            .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        cartItem.setQuantity(quantity);
        cartRepository.save(cartItem);
    }

}
