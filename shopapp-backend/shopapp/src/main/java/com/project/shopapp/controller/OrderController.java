package com.project.shopapp.controller;

import com.project.shopapp.dto.request.OrderDTO;
import com.project.shopapp.dto.response.OrderResponse;
import com.project.shopapp.dto.response.ResponseData;
import com.project.shopapp.dto.response.ResponseError;
import com.project.shopapp.model.Order;
import com.project.shopapp.service.impl.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("")
    public ResponseData<OrderResponse> createOrder(@Valid @RequestBody OrderDTO order) {
        try {
            log.info("Request create order = {}", order.getFullName());
            Order newOrder =  orderService.createOrder(order);
            return new ResponseData<>(HttpStatus.CREATED.value(), "order create successfully", orderService.convertToOrderResponse(newOrder));
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "create order fail");
        }
    }

    @PutMapping("/{orderId}")
    // công việc của admin
    public ResponseData<OrderResponse> updateOrder(@Valid @PathVariable("orderId") Long id, @Valid @RequestBody OrderDTO order) {
        try {
            log.info("Request update order = {}", order.getFullName());
            Order newOrder = orderService.updateOrder(id, order);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "order update successfully", orderService.convertToOrderResponse(newOrder));
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "update order fail");
        }
    }
    @GetMapping("/{orderId}")
    public ResponseData<OrderResponse> getOrderById(@Valid @PathVariable("orderId") Long id) {
        try {
            log.info("Get product by id = {}", id);
            Order order = orderService.getOrder(id);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get product by id successfully", orderService.convertToOrderResponse(order));
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "get order by id fail");
        }
    }
    @GetMapping("")
    public ResponseData<List<OrderDTO>> getListOfOrder(@RequestParam(defaultValue = "0", required = false) int page,
                                            @RequestParam(defaultValue = "0", required = false) int limit) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get list of order successfully", null);
    }
    @DeleteMapping("/{orderId}")
    public ResponseData<?> deleteOrder(@Valid @PathVariable("orderId") Long id) {
        // xóa mềm => cập nhật trường active = false
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "delete order successfully with id = " + id);
    }
}
