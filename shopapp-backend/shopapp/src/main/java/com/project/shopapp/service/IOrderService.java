package com.project.shopapp.service;

import com.project.shopapp.dto.request.OrderDTO;
import com.project.shopapp.dto.response.OrderResponse;
import com.project.shopapp.model.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO);

    Order getOrder(Long id);

    Order updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrder(Long id);

    List<Order> findByUserId(Long userId);
}
