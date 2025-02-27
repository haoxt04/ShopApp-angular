package com.project.shopapp.controller;
import com.project.shopapp.dto.request.OrderDetailDTO;
import com.project.shopapp.dto.response.ResponseData;
import com.project.shopapp.dto.response.ResponseError;
import com.project.shopapp.model.OrderDetail;
import com.project.shopapp.repository.OrderDetailRepository;
import com.project.shopapp.service.impl.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @PostMapping("")
    public ResponseData<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetail orderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return new ResponseData<>(HttpStatus.CREATED.value(), "order_detail create successfully", orderDetail);
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "create order_detail fail");
        }
    }

    @PutMapping("/{odId}")
    public ResponseData<?> updateOrderDetail(@Valid @PathVariable("odId") Long id,@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "order_detail update successfully", orderDetail);
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "update order_detail fail");
        }
    }

    // lấy danh sách các order_detail của 1 order nào đó
    @GetMapping("/order/{orderId}")
    public ResponseData<?> getOrderDetailByOrderId(@Valid @PathVariable("orderId") Long id) {
        try {
            List<OrderDetail> orderDetails = orderDetailService.findByOrderId(id);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get order_detail by order id successfully", orderDetails);
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "get order_detail by order id fail");
        }
    }

    @GetMapping("/{odId}")
    public ResponseData<?> getOrderDetails(@Valid @PathVariable("odId") Long id) {
        try {
            OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get order_detail by id successfully", orderDetail);
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "get order_detail by id fail");
        }
    }
    @DeleteMapping("/{odId}")
    public ResponseData<?> deleteOrderDetail(@Valid @PathVariable("odId") Long id) {
        try {
            orderDetailService.deleteById(id);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "delete order_detail by id successfully", id);
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "delete order_detail by id fail");
        }
    }
}
