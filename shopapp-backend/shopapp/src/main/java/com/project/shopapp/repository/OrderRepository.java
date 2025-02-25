package com.project.shopapp.repository;

import com.project.shopapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // tìm đơn hàng của một user nào đó
    List<Order> findByUserId(Long userId);
}
