package com.example.computerstore.service;

import com.example.computerstore.Payload.OrderPayLoad;

import java.util.ArrayList;
import java.util.HashMap;

public interface OrderPayloadService {

    public HashMap<Integer, OrderPayLoad> addOrder(int id, int quantity,HashMap<Integer, OrderPayLoad> orderDetail);


    public HashMap<Integer,OrderPayLoad> upDateOrder (int id, int quantity, HashMap<Integer, OrderPayLoad> orderPayLoads);

    public HashMap<Integer, OrderPayLoad> DeleteOrder (int id, HashMap<Integer, OrderPayLoad> orderPayLoads);

    int totalQuantity (HashMap<Integer, OrderPayLoad> totalQuantity);

    int totalPrice(HashMap<Integer, OrderPayLoad> totalPrice);

}
