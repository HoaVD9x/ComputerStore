package com.example.computerstore.service;

import com.example.computerstore.Payload.ProductPayload;
import com.example.computerstore.dao.CategoryRepository;
import com.example.computerstore.dao.ProductRepository;
import com.example.computerstore.model.Product;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;

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
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Value("${static.file.location}")
    private String staticResource;
    @Autowired
    private ProductRepository productsRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void save(ProductPayload productPayload, HttpServletRequest request) throws IOException {
       Product product = new Product();
       product.setProductId(productPayload.getProductId());
       product.setProductName(productPayload.getProductName());
       product.setProductPrice(productPayload.getProductPrice());
       product.setQuantity(productPayload.getQuantity());
       product.setProductDescription(productPayload.getProductDescription());
       product.setCategory(productPayload.getCategory());
       product.setBrand(productPayload.getBrand());

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
            product.setProductImageLink(uri.concat(File.separator).concat("public").concat(File.separator).concat(productImage.getOriginalFilename()));

        }

        productsRepository.save(product);
    }

    @Override
    public Page<Product> getProductByCategoryName(String categoryName, Pageable pageable) {
        return  productsRepository.findProductByCategory_CategoryNameAndActiveTrue(categoryName, pageable);
    }

    @Override
    public Page<Product> getProductbyBrand(String brand, Pageable pageable) {
        return productsRepository.findProductsByBrandAndActiveTrue(brand, pageable);
    }

    @Override
    public List<Product> findAll() {
        return productsRepository.findAllByActiveTrue();
    }

    @Override
    public Optional<Product> getProductById(int productId) {
        return productsRepository.getProductsByProductId(productId);
    }

    @Override
    public Page<Product> getProductByMinMaxPrice(int min, int max, Pageable pageable) {
        return productsRepository.findProductsByProductPriceIsBetweenAndActiveTrue(min, max, pageable);
    }

    @Override
    public List<Product> getProductPriceMin(int min) {
        return productsRepository.findProductsByProductPriceAfter(min);
    }

    @Override
    public Product saveProduct(Product product) {
        return productsRepository.save(product);
    }

    @Override
    public List<Product> findAllProductByBrand(String brand) {
        return productsRepository.getProductsByBrand(brand);
    }

    @Override
    public Page<Product> getProductByKeyWord(String keyWord, Pageable pageable) {
        return productsRepository.findProductByProductNameIsContainingAndActiveTrue(keyWord, pageable);
    }


}
