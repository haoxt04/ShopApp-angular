package com.project.shopapp.service;

import com.project.shopapp.dto.request.OrderDetailDTO;
import com.project.shopapp.model.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO newOrderDetail);

    OrderDetail getOrderDetail(Long id);

    OrderDetail updateOrderDetail(Long id, OrderDetailDTO newOrderDetail);

    void deleteById(Long id);

    List<OrderDetail> findByOrderId(Long orderId);
}
