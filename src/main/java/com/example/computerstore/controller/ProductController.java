package com.example.computerstore.controller;

import com.example.computerstore.Payload.OrderPayLoad;
import com.example.computerstore.dao.CategoryRepository;
import com.example.computerstore.dao.ProductRepository;
import com.example.computerstore.model.Product;
import com.example.computerstore.model.User;
import com.example.computerstore.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;


    @GetMapping({"/", "search"})
    public String AllProduct(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam(name = "search", required = false) String keyWord,
            HttpSession httpSession) {
        Page<Product> productsPage = null;
        // pageable
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        if (StringUtils.hasText(keyWord)) {
            productsPage = productService.getProductByKeyWord(keyWord, PageRequest.of(currentPage - 1, pageSize));
            if (productsPage.isEmpty()) {
                model.addAttribute("message", "product is not present !");
            }
        } else {
            productsPage = productRepository.getProductsByActiveTrue(PageRequest.of(currentPage - 1, pageSize));
        }
        model.addAttribute("productPage", productsPage);
        int totalPages = productsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("products", productsPage);

        //search

        // category
        model.addAttribute("categorys", categoryRepository.findAllByActiveTrue());

        // brand
        List<Product> productList = productService.findAll();
        List<String> brandList = productList.stream().map(Product::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);

        // order
        model.addAttribute("orderPayload", new OrderPayLoad());

        //pageable
        model.addAttribute("pathvariable", "products");

        // order
        List<Product> slideProduct = productService.findAllProductByBrand("apple");
        model.addAttribute("slides", slideProduct);

        // user
        model.addAttribute("user", new User());

        return "home/index";
    }

    @GetMapping("detail")
    public String detailProduct(@RequestParam("productId") int productId,
                                Model model) {
        Optional<Product> productOptional = productService.getProductById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);
            model.addAttribute("user", new User());
            model.addAttribute("orderPayload", new OrderPayLoad());

        } else {
            model.addAttribute("user", new User());

            model.addAttribute("message", "Product is not present !");
        }
        return "product/productDetail";
    }


    @GetMapping("category")
    public String getProductByCategoryId(Model model,
                                         @RequestParam("categoryName") String categoryName,
                                         @RequestParam("page") Optional<Integer> page,
                                         @RequestParam("size") Optional<Integer> size) {

        // pageable
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        Page<Product> productsPage = productService.getProductByCategoryName(categoryName, PageRequest.of(currentPage - 1, pageSize));
        if (productsPage.isEmpty()){
            model.addAttribute("message","product is not present !");
        }
        model.addAttribute("productPage", productsPage);
        int totalPages = productsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("products", productsPage);


        List<Product> productList = productService.findAll();
        model.addAttribute("categorys", categoryRepository.findAllByActiveTrue());


        model.addAttribute("pathvariable", "categoryId");


        List<String> brandList = productList.stream().map(Product::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);


        return "home/index";
    }

    @GetMapping("brand/{brand}")
    public String getProductByBrand(@PathVariable("brand") String brand,
                                    Model model,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) {

        // pageable
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        Page<Product> productsPage = productService.getProductbyBrand(brand, PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("productPage", productsPage);
        int totalPages = productsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("products", productsPage);


        List<Product> productList = productService.findAll();
        model.addAttribute("categorys", categoryRepository.findAllByActiveTrue());


        List<String> brandList = productList.stream().map(Product::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);


        model.addAttribute("pathvariable", "brand");

        return "home/index";
    }

    @GetMapping("price")
    public String getProductByPrice(Model model,
                                    @RequestParam("min") int min,
                                    @RequestParam("max") int max,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) {

        // pageable
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        Page<Product> productsPage = productService.getProductByMinMaxPrice(min, max, PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("productPage", productsPage);
        int totalPages = productsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("products", productsPage);


        // category
        model.addAttribute("categorys", categoryRepository.findAllByActiveTrue());

        // brand
        List<Product> productList = productService.findAll();
        List<String> brandList = productList.stream().map(Product::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);

        model.addAttribute("pathvariable", "price");

        return "home/index";
    }

    @GetMapping("MinPrice")
    public String getProductByPriceMin(Model model,
                                       @RequestParam("min") int min,
                                       @RequestParam("page") Optional<Integer> page,
                                       @RequestParam("size") Optional<Integer> size) {

        // pageable
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        Page<Product> productsPage = productRepository.findAll(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("productPage", productsPage);
        int totalPages = productsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("products", productsPage);
        List<Product> getMinPrice = productService.getProductPriceMin(min);
        model.addAttribute("products", getMinPrice);
        // category
        model.addAttribute("categorys", categoryRepository.findAllByActiveTrue());

        // brand
        List<Product> productList = productService.findAll();
        List<String> brandList = productList.stream().map(Product::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);


        return "home/index";
    }
}
