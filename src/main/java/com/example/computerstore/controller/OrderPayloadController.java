package com.example.computerstore.controller;

import com.example.computerstore.Payload.OrderPayLoad;
import com.example.computerstore.service.OrderPayloadService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class OrderPayloadController {
    @Autowired
    private OrderPayloadService orderPayloadService;

    @GetMapping("addOrder")
    public String addOrder(HttpServletRequest request,
                           HttpSession httpSession,
                           Model model,
                           @RequestParam("productId") int productId,
                           @RequestParam("quantity") int quantity) {

        HashMap<Integer, OrderPayLoad> orderPayLoads = (HashMap<Integer, OrderPayLoad>) httpSession.getAttribute("orderSession");
        if (orderPayLoads == null) {
            orderPayLoads = new HashMap<>();
        }
        orderPayLoads = orderPayloadService.addOrder(productId, quantity, orderPayLoads);
        httpSession.setAttribute("orderSession", orderPayLoads);
        httpSession.setAttribute("totalOrderSession", orderPayLoads.size());
        if (orderPayLoads.isEmpty()) {
            httpSession.setAttribute("orderSession", null);
        }
        httpSession.setAttribute("totalPriceSession", orderPayloadService.totalPrice(orderPayLoads));
        httpSession.setAttribute("totalQuantityOrderSession", orderPayloadService.totalQuantity(orderPayLoads));
        return "redirect:" + request.getHeader("Referer");
    }


    @GetMapping("viewOrder")
    public String viewOrder(Model model) {
        return "product/cart";
    }

    @GetMapping("UpDateOrder")
    public String upDateOrder (Model model,
                               @RequestParam("productId") int productId,
                               @RequestParam("quantity") int quantity,
                               HttpServletRequest request,
                               HttpSession httpSession) {
        HashMap<Integer, OrderPayLoad> orderPayLoads = (HashMap<Integer, OrderPayLoad>) httpSession.getAttribute("orderSession");
        orderPayLoads = orderPayloadService.upDateOrder(productId, quantity, orderPayLoads);
        httpSession.setAttribute("orderSession", orderPayLoads);
        httpSession.setAttribute("totalOrderSession", orderPayLoads.size());
        if (orderPayLoads.isEmpty()) {
            httpSession.setAttribute("orderSession", null);
        }
        httpSession.setAttribute("totalPriceSession", orderPayloadService.totalPrice(orderPayLoads));
        httpSession.setAttribute("totalQuantityOrderSession", orderPayloadService.totalQuantity(orderPayLoads));
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("deleteOrder")
    public String deleteOrder (@RequestParam("productId")int productId,
                               HttpSession httpSession,
                               HttpServletRequest request){
        HashMap<Integer, OrderPayLoad> orderPayLoads = (HashMap<Integer, OrderPayLoad>) httpSession.getAttribute("orderSession");
        orderPayLoads = orderPayloadService.DeleteOrder(productId, orderPayLoads);
        httpSession.setAttribute("orderSession", orderPayLoads);
        if (orderPayLoads.size() == 0) {
            httpSession.setAttribute("orderSession", null);
        }
        httpSession.setAttribute("totalOrderSession", orderPayLoads.size());
        httpSession.setAttribute("totalPriceSession", orderPayloadService.totalPrice(orderPayLoads));
        httpSession.setAttribute("totalQuantityOrderSession", orderPayloadService.totalQuantity(orderPayLoads));
        return "redirect:" + request.getHeader("Referer");

    }
}
