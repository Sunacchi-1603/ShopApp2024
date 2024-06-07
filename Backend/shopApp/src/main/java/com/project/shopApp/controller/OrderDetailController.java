package com.project.shopApp.controller;

import com.project.shopApp.dtos.OrderDTO;
import com.project.shopApp.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/orderDetail")
public class OrderDetailController {
    @PostMapping("")
    public ResponseEntity<?> insertOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO,
            BindingResult result
            ){
        return ResponseEntity.ok("insser");
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetailByOrderId(@Valid @PathVariable("orderId") Long orderId){
        return ResponseEntity.ok(orderId);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id){
        return ResponseEntity.ok("order detail wwith "+ id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable Long order_id,
            @RequestBody OrderDetailDTO orderDetailDTO
    ){
        return ResponseEntity.ok("Cap nhat order detail thanh cong");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderId(@Valid @PathVariable("id") Long id){
        return ResponseEntity.noContent().build();
    }
}
