package com.project.shopapp.service.impl;
import com.project.shopapp.dto.request.OrderDTO;
import com.project.shopapp.model.Order;
import com.project.shopapp.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailService implements IOrderService {
    @Override
    public Order createOrder(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public Order getOrder(Long id) {
        return null;
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return List.of();
    }
}
