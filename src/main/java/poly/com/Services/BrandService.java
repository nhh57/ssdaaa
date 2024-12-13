package poly.com.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.com.Repository.BrandRepo;
import poly.com.Entity.Brand;

@Service
public class BrandService {

    @Autowired
    private BrandRepo brandRepository;

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(int id) {
        return brandRepository.findById(id).orElse(null);
    }
}

