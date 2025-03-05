package com.project.shopapp.controller;
import com.project.shopapp.component.LocalizationUtils;
import com.project.shopapp.dto.request.OrderDetailDTO;
import com.project.shopapp.dto.response.OrderDetailResponse;
import com.project.shopapp.dto.response.ResponseData;
import com.project.shopapp.dto.response.ResponseError;
import com.project.shopapp.model.OrderDetail;
import com.project.shopapp.service.impl.OrderDetailService;
import com.project.shopapp.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    private final LocalizationUtils localizationUtils;

    @PostMapping("")
    public ResponseData<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetail orderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return new ResponseData<>(HttpStatus.CREATED.value(), "order_detail create successfully", OrderDetailResponse.fromOrderDetail(orderDetail));
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "create order_detail fail");
        }
    }

    @PutMapping("/{odId}")
    public ResponseData<?> updateOrderDetail(@Valid @PathVariable("odId") Long id,@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "order_detail update successfully", OrderDetailResponse.fromOrderDetail(orderDetail));
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
            List<OrderDetailResponse> orderDetailResponses = orderDetails
                    .stream()
                    .map(OrderDetailResponse::fromOrderDetail)
                    .toList();
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get order_detail by order id successfully", orderDetailResponses);
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "get order_detail by order id fail");
        }
    }

    @GetMapping("/{odId}")
    public ResponseData<?> getOrderDetails(@Valid @PathVariable("odId") Long id) {
        try {
            OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get order_detail by id successfully", OrderDetailResponse.fromOrderDetail(orderDetail));
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "get order_detail by id fail");
        }
    }
    @DeleteMapping("/{odId}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("odId") Long id) {
        try {
            orderDetailService.deleteById(id);
            return ResponseEntity.ok(localizationUtils.getLocalizedMessage(MessageKeys.DELETE_ORDER_DETAIL_SUCCESSFULLY));
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
