package com.project.shopapp.service.impl;
import com.project.shopapp.dto.request.OrderDetailDTO;
import com.project.shopapp.model.OrderDetail;
import com.project.shopapp.service.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailService implements IOrderDetailService {

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO newOrderDetail) {
        return null;
    }

    @Override
    public OrderDetail getOrderDetail(Long id) {
        return null;
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO newOrderDetail) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return List.of();
    }
}
