package poly.com.Services;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import poly.com.Repository.ProductDiscountRepo;
import poly.com.data.ProductDataModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDiscountService {

    @Resource
    private ProductDiscountRepo repo;


    public List<ProductDataModel> getProductDiscountss(String name, Integer categoryId, Double minPrice, Double maxPrice) {
        if (categoryId == null) {
            categoryId = 0;
        }
        if (name == null || name.isEmpty()) {
            name = "";
        }
        if (minPrice == null) {
            minPrice = 0.0;
        }
        if (maxPrice == null) {
            maxPrice = 0.0;
        }
        return repo.findByProducts(name, categoryId, minPrice, maxPrice).stream().map(obj -> {
            ProductDataModel product = new ProductDataModel();
            product.setId((Integer) obj[0]);
            product.setName((String) obj[1]);
            product.setPrice((BigDecimal) obj[2]);
            product.setDiscount(obj[3] == null ? BigDecimal.valueOf(0) : (BigDecimal) obj[3]);
            product.setDescription((String) obj[4]);
            product.setQuantity((Integer) obj[5]);
            product.setImage((String) obj[6]);
            return product;
        }).collect(Collectors.toList());
    }
}
