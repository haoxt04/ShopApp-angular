package com.project.shopapp.controller;

import com.project.shopapp.dto.request.OrderDTO;
import com.project.shopapp.dto.response.ResponseData;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @PostMapping("")
    public ResponseData<Integer> createOrder(@Valid @RequestBody OrderDTO order) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "order create successfully", 1);
    }

    @PutMapping("/{orderId}")
    // công việc của admin
    public ResponseData<?> updateOrder(@Valid @PathVariable("orderId") Long id, @Valid @RequestBody OrderDTO order) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "order update successfully with id = " + id);
    }
    @GetMapping("/{orderId}")
    public ResponseData<OrderDTO> getProductById(@Valid @PathVariable("orderId") Long id) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get order successfully with id = " + id, null);
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
