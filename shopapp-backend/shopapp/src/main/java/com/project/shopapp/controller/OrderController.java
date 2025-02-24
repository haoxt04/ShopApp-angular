package com.project.shopapp.controller;

import com.project.shopapp.dto.Order;
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
    public ResponseEntity<?> createOrder(@RequestBody @Valid Order order, BindingResult result) {
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long orderId) {
        try {
            return ResponseEntity.ok("Get order by id = " + orderId);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("")
    public ResponseEntity<?> getListOfOrder(@RequestParam("page") int page, @RequestParam("limit") int size) {
        return ResponseEntity.ok("get list of Order successfully");
    }

    @PutMapping("/{id}")
    // công việc của admin
    public ResponseEntity<?> updateOrder(@Valid @PathVariable Long id, @Valid @RequestBody Order order) {
        return ResponseEntity.ok("update order successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable Long id) {
        // xóa mềm => cập nhật trường active = false
        return ResponseEntity.ok("delete order successful");
    }
}
