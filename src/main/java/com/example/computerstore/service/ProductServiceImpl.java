package com.example.computerstore.service;

import com.example.computerstore.Payload.ProductPayload;
import com.example.computerstore.dao.ProductRepository;
import com.example.computerstore.model.Products;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository repository;

    @Value("${static.file.location}")
    private String staticResource;

    @Override
    public void save(ProductPayload productPayload, HttpServletRequest request) throws IOException {
       Products  products = new Products();
       products.setProductId(productPayload.getProductId());
       products.setProductName(productPayload.getProductName());
       products.setProductPrice(productPayload.getProductPrice());
       products.setQuantity(productPayload.getQuantity());
       products.setProductDescription(productPayload.getProductDescription());
       products.setCategory(productPayload.getCategory());
       products.setBrand(productPayload.getBrand());

        FileOutputStream stream = null;
        if (StringUtils.hasText(productPayload.getFile().getOriginalFilename())) {
            MultipartFile productImage = productPayload.getFile();
            try {
                Path path = Paths.get(staticResource);
                if (!Files.exists(path) )
                    Files.createDirectories(path);
                File file = new File(staticResource + productImage.getOriginalFilename());
                file.createNewFile();
                stream = new FileOutputStream(file);
                stream.write(productImage.getBytes());
            } catch (IOException exception) {
                throw new RuntimeException();
            } finally {
                if (stream != null) stream.close();
            }
            String uri = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            products.setProductImageLink(uri.concat(File.separator).concat("public").concat(File.separator).concat(productImage.getOriginalFilename()));

        }

        repository.save(products);
    }



    @Override
    public List<Products> getAll() {
        return  repository.findAll();
    }

    @Override
    public List<Products> getProductByCategoryId(int categoryId) {
        return repository.findByCategory_CategoryId(categoryId);
    }

    @Override
    public List<Products> getProductbyBrand(String brand) {
        return repository.findProductsByBrand(brand);
    }

    @Override
    public Products searchProduct(int productId) {
        return repository.getByProductId(productId);
    }
}
