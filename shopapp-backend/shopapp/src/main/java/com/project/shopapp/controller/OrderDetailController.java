package com.project.shopapp.controller;

import com.project.shopapp.dto.request.OrderDetailDTO;
import com.project.shopapp.dto.response.ResponseData;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {

    @PostMapping("")
    public ResponseData<Integer> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetail) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "order_detail create successfully", 1);
    }

    @PutMapping("/{odId}")
    public ResponseData<?> updateOrderDetail(@Valid @PathVariable("odId") Long id,@Valid @RequestBody OrderDetailDTO newOrderDetail) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "update order_detail successfully with id = ");
    }
    @GetMapping("/{odId}")
    public ResponseData<OrderDetailDTO> getOrderDetail(@Valid @PathVariable("odId") Long id) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get order_detail successfully with id = " + id, null);
    }
    // lấy danh sách các order_detail của 1 order nào đó
    @GetMapping("/order/{orderId}")
    public ResponseData<List<OrderDetailDTO>> getOrderDetails(@Valid @PathVariable("orderId") Long orderId) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get list order_detail of order with id = " + orderId, null);
    }
    @DeleteMapping("/{odId}")
    public ResponseData<?> deleteOrderDetail(@Valid @PathVariable("odId") Long id) {
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "delete order_detail successfully with id = " + id);
    }
}
