package poly.com.Services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.com.Entity.Category;
import poly.com.Repository.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null); // Trả về danh mục nếu tìm thấy, ngược lại trả null
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category); // Lưu hoặc cập nhật danh mục
    }

    @Override
    public void deleteCategoryById(int id) {
        categoryRepository.deleteById(id); // Xóa danh mục theo ID
    }
}
