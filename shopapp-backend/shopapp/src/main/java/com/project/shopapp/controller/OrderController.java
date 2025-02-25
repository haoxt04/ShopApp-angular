package com.project.shopapp.controller;

import com.project.shopapp.dto.request.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO order, BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok("createOrder successfully");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getProductById(@Valid @PathVariable("orderId") Long orderId) {
        try {
            return ResponseEntity.ok("Get order by id = " + orderId);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("")
    public ResponseEntity<?> getListOfOrder(@RequestParam(defaultValue = "0", required = false) int page,
                                            @RequestParam(defaultValue = "0", required = false) int limit) {
        return ResponseEntity.ok("get list of OrderDTO successfully");
    }

    @PutMapping("/{orderId}")
    // công việc của admin
    public ResponseEntity<?> updateOrder(@Valid @PathVariable("orderId") Long id, @Valid @RequestBody OrderDTO order) {
        return ResponseEntity.ok("update order successfully");
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable("orderId") Long id) {
        // xóa mềm => cập nhật trường active = false
        return ResponseEntity.ok("delete order successful");
    }
}
