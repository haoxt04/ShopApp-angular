package com.project.shopapp.controller;

import com.project.shopapp.dto.request.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetail) {
        return ResponseEntity.ok("create order_detail successful");
    }

    @GetMapping("/{odId}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("odId") Long id) {
        return ResponseEntity.ok("get order_detail by id = " + id);
    }

    // lấy danh sách các order_detail của 1 order nào đó
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok("get order details with orderId = " + orderId);
    }

    @PutMapping("/{odId}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("odId") Long id,@Valid @RequestBody OrderDetailDTO newOrderDetail) {
        return ResponseEntity.ok("updateOrderDetail with id = " + id);
    }

    @DeleteMapping("/{odId}")
    public ResponseEntity<Void> deleteOrderDetail(@Valid @PathVariable("odId") Long id) {
        return ResponseEntity.noContent().build();
    }
}
