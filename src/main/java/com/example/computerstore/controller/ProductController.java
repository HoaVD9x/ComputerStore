package com.example.computerstore.controller;

import com.example.computerstore.Payload.OrderPayLoad;
import com.example.computerstore.Payload.ProductPayload;
import com.example.computerstore.dao.CategoryRepository;
import com.example.computerstore.dao.ProductRepository;
import com.example.computerstore.dao.UserRepositoty;
import com.example.computerstore.model.Product;
import com.example.computerstore.model.User;
import com.example.computerstore.service.ProductService;
import com.example.computerstore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
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


    @GetMapping("/")
    public String AllProduct(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        // pageable
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        Page<Product> productsPage = productRepository.getProductsByActiveTrue(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("productPage", productsPage);
        int totalPages = productsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("products", productsPage);

        // category
        model.addAttribute("categorys", categoryRepository.findAll());

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

        return "product/index";
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
        return "product/ProductDetail";
    }

    @GetMapping("category")
    public String getProductByCategoryId(Model model,
                                         @RequestParam("categoryId") int categoryId,
                                         @RequestParam("page") Optional<Integer> page,
                                         @RequestParam("size") Optional<Integer> size) {

        // pageable
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        Page<Product> productsPage = productService.getProductByCategoryId(categoryId, PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("productPage", productsPage);
        int totalPages = productsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("products", productsPage);


        List<Product> productList = productService.findAll();
        model.addAttribute("categorys", categoryRepository.findAll());


        model.addAttribute("pathvariable", "categoryId");


        List<String> brandList = productList.stream().map(Product::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);




        return "product/index";
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
        model.addAttribute("categorys", categoryRepository.findAll());


        List<String> brandList = productList.stream().map(Product::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);


        model.addAttribute("pathvariable", "brand");

        return "product/index";
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
        model.addAttribute("categorys", categoryRepository.findAll());

        // brand
        List<Product> productList = productService.findAll();
        List<String> brandList = productList.stream().map(Product::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);

        model.addAttribute("pathvariable", "price");

        return "product/index";
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
        model.addAttribute("categorys", categoryRepository.findAll());

        // brand
        List<Product> productList = productService.findAll();
        List<String> brandList = productList.stream().map(Product::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);



        return "product/index";
    }

//    @GetMapping("delete")
//    public String deleteProduct(@RequestParam("productId") int productId,
//                                Model model,
//                                @RequestParam("page") Optional<Integer> page,
//                                @RequestParam("size") Optional<Integer> size) {
//
//        // pageable
//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(8);
//        Page<Product> productsPage = productRepository.findAll(PageRequest.of(currentPage - 1, pageSize));
//        model.addAttribute("productPage", productsPage);
//        int totalPages = productsPage.getTotalPages();
//        if (totalPages > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
//        model.addAttribute("products", productsPage);
//
//        Optional<Product> productOptional = productService.getProductById(productId);
//        if (productOptional.isPresent()){
//            Product product = productOptional.get();
//            product.setActive(false);
//            productService.saveProduct(product);
//            model.addAttribute("message", "successful delete " + product.getProductName());
//            List<Product> productList = productService.findAll();
//            model.addAttribute("products", productList);
//            model.addAttribute("categorys", categoryRepository.findAll());
//
//
//            model.addAttribute("user", new User());
//            return "redirect:/admin/product";
//        } else {
//            model.addAttribute("message", "product is not present !");
//            return "redirect:/admin/product/";
//        }
//
//
//
//
//
//
//    }


//    @PostMapping("saveProduct")
//    public ModelAndView saveProduct(@Valid ProductPayload productPayload,
//                                    BindingResult result,
//                                    HttpServletRequest request,
//                                    ModelMap modelMap) throws IOException {
//        if (result.hasErrors()) {
//            return new ModelAndView("NewProduct");
//        }
//        productService.save(productPayload, request);
//        modelMap.addAttribute("message", "Product is Saved");
//        return new ModelAndView("forward:/admin/product/newProduct", modelMap);
//
//    }
}
