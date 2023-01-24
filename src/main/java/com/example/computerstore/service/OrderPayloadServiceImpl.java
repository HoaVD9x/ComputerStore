package com.example.computerstore.service;

import com.example.computerstore.Payload.OrderPayLoad;
import com.example.computerstore.dao.OrderDetailRepository;
import com.example.computerstore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.cglib.core.CollectionUtils.filter;

@Service
public class OrderPayloadServiceImpl implements OrderPayloadService {

    @Autowired
    private ProductService productService;


    @Override
    public HashMap<Integer, OrderPayLoad> addOrder(int id, int quantity, HashMap<Integer, OrderPayLoad> orderDetail) {
        OrderPayLoad orderPayLoad = new OrderPayLoad();
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent() && orderDetail.containsKey(id)) {
            orderPayLoad = orderDetail.get(id);
            orderPayLoad.setQuantityOrder(orderDetail.get(id).getQuantityOrder() + quantity);
            orderPayLoad.setTotalPrice(orderPayLoad.getQuantityOrder() * orderPayLoad.getProduct().getProductPrice());

        } else {
            orderPayLoad.setProduct(product.get());
            orderPayLoad.setQuantityOrder(quantity);
            orderPayLoad.setTotalPrice(product.get().getProductPrice() * orderPayLoad.getQuantityOrder());
        }
        orderDetail.put(id, orderPayLoad);
        return orderDetail;
    }

    @Override
    public HashMap<Integer, OrderPayLoad> upDateOrder(int id, int quantity, HashMap<Integer, OrderPayLoad> detailOrder) {
        OrderPayLoad orderPayLoad = new OrderPayLoad();

        if (detailOrder.containsKey(id)) {
            orderPayLoad = detailOrder.get(id);
            orderPayLoad.setQuantityOrder(quantity);
            orderPayLoad.setTotalPrice(quantity * orderPayLoad.getProduct().getProductPrice());
        }
        detailOrder.put(id, orderPayLoad);
        return detailOrder;
    }

    @Override
    public HashMap<Integer, OrderPayLoad> DeleteOrder(int id, HashMap<Integer, OrderPayLoad> detailOrder) {
        if (detailOrder != null) {
            if (detailOrder.containsKey(id)) {
                detailOrder.remove(id);
            }
        }
        return detailOrder;
    }

    @Override
    public int totalQuantity(HashMap<Integer, OrderPayLoad> totalQuantity) {
        int totalQuantitys = 0;
        for (Map.Entry<Integer, OrderPayLoad> oderPayLoad : totalQuantity.entrySet()) {

            totalQuantitys += oderPayLoad.getValue().getQuantityOrder();
        }
        return totalQuantitys;
    }

    @Override
    public int totalPrice(HashMap<Integer, OrderPayLoad> totalPrice) {
        int mapValue = totalPrice.values().stream().map(s -> s.getTotalPrice()).reduce(0, (a, b) -> a + b);
        return mapValue;
    }


}
