package poly.com.Services;

import java.util.List;
import poly.com.Entity.Category;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    void saveCategory(Category category);
    void deleteCategoryById(int id);
}
