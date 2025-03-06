package com.project.shopapp.service.impl;

import com.project.shopapp.dto.request.OrderDTO;
import com.project.shopapp.dto.response.OrderResponse;
import com.project.shopapp.exception.ResourceNotFoundException;
import com.project.shopapp.model.Order;
import com.project.shopapp.model.OrderStatus;
import com.project.shopapp.model.User;
import com.project.shopapp.repository.OrderRepository;
import com.project.shopapp.repository.UserRepository;
import com.project.shopapp.service.IOrderService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService implements IOrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
    }


    @Override
    public Order createOrder(OrderDTO orderDTO) {
        // tim xem id có tồn tại hay không
        User user = findUserById(orderDTO);

        // convert OrderDTO => Order model
        // dùng thư viện Model Mapper
        // Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ
        Order order = modelMapper.map(orderDTO, Order.class);

        // câp nhật các trường của order dto
        order.setUser(user);
        order.setOrderDate(new Date());     // lấy thời điểm hiện tại
        order.setStatus(OrderStatus.PENDING);
        order.setShippingDate(validateShipDate(orderDTO.getShippingDate()));
        order.setActive(true);

        orderRepository.save(order);
        log.info("order has saved");
        return order;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("order not found"));
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTO) {
        Order order = getOrder(id);
        User existsUser = findUserById(orderDTO);

        modelMapper.map(orderDTO, order);

        order.setUser(existsUser);
        order.setShippingDate(validateShipDate(orderDTO.getShippingDate()));

        log.info("order updated , orderId = {}", id);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        // xóa mềm: active = false
        log.info("order deleted, orderId =  {}", id);
        Order order = getOrder(id);
        order.setActive(false);
        orderRepository.save(order);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    private User findUserById(OrderDTO orderDTO) {
        return userRepository.findById(orderDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    // kiểm tra ngày ship
    private LocalDate validateShipDate(LocalDate shippingDate) {
        return Optional.ofNullable(shippingDate)
                .filter(date -> !date.isBefore(LocalDate.now()))
                .orElse(LocalDate.now());
    }

    public OrderResponse convertToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .fullName(order.getFullName())
                .email(order.getEmail())
                .phoneNumber(order.getPhoneNumber())
                .address(order.getAddress())
                .note(order.getNote())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalMoney(Float.parseFloat(order.getTotalMoney()))
                .shippingMethod(order.getShippingMethod())
                .shippingAddress(order.getShippingAddress())
                .shippingDate(order.getShippingDate())
                .paymentMethod(order.getPaymentMethod())
                .build();
    }

}
