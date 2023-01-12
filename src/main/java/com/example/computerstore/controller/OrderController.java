package com.example.computerstore.controller;

import com.example.computerstore.Payload.OrderPayLoad;
import com.example.computerstore.dao.OrderRepository;
import com.example.computerstore.model.Order;
import com.example.computerstore.model.Products;
import com.example.computerstore.service.CartService;
import com.example.computerstore.service.OrderService;
import com.example.computerstore.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;

    @GetMapping("order/{productId}" )
    public String order(@PathVariable("productId") int productId,
                        @RequestParam("quantityOrder") int quantityOrder,
                        Model model){
        Products product = productService.getProductById(productId);
        OrderPayLoad orderPayLoad = new OrderPayLoad();
        orderPayLoad.setProduct(product);
        orderPayLoad.setQuantityOrder(quantityOrder);
        orderService.save(orderPayLoad);
//        Order order = orderService.findOrderByOrderPayLoadId(orderPayLoad.getOrderId());
//        cartService.save(order);
        model.addAttribute("message","The product has been successfully added to the cart ! ");
        return "product/products";
    }

    @GetMapping("deleteOrder")
    public  String deleteOrder(Model model, @RequestParam ("orderId") int  orderId) {
        orderService.deleteOrder(orderId);
        model.addAttribute("message", "product has been successfully deleted");
        return "product/cart";
    }

    @PostMapping("updateOrder")
    public String updateOrder(@RequestParam("quantityOrder")int quantityOrder,
                              @RequestParam("orderId") int orderId,
                              Model model){
        if (quantityOrder != 0 ){
            Order order = orderService.findOrderBYId(orderId);
            order.setQuantityOrder(quantityOrder);
            orderService.SaveOrder(order);
            model.addAttribute("message","update:" + order.getProduct().getProductName() + "quantity Order :" + order.getQuantityOrder());

        } else {
            orderService.deleteOrder(orderId);
        }

        //get All list Order
        List<Order> orderList = orderService.findAll();
        orderList.stream().filter(order1 -> order1.getQuantityOrder() > 0).collect(Collectors.toList());
        model.addAttribute("cartLists",orderList);

        //sum price
        List<Integer> listPrice = orderList.stream().map(order2 -> order2.getProduct().getProductPrice() * order2.getQuantityOrder()).collect(Collectors.toList());
        int sumPrice = listPrice.stream().reduce(0, (a, b) -> a + b);
        model.addAttribute("sumPrice", sumPrice);
        return "product/cart";
    }
}
