package com.project.shopApp.controller;

import com.project.shopApp.dtos.OrderDTO;
import com.project.shopApp.exeptions.DataNotFoundException;
import com.project.shopApp.models.Order;
import com.project.shopApp.services.IOrderRervice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderRervice orderService;
    private final com.project.shopApp.components.LocalizationUtils localizationUtils;
    @PostMapping
    public ResponseEntity<?> insertOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult result

    ){
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Order orderResponse = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }    }
    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getUserOrders(@Valid @PathVariable("user_id") Long userId){
        try{
            List<Order> orders = orderService.findByUserId(userId);
            return ResponseEntity.ok(orders);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("id") Long orderId){
        try{
           Order order = orderService.getOrder(orderId);
            return ResponseEntity.ok(order);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable("id") Long order_id,
            @RequestBody OrderDTO orderDTO
    ){
        try {
            Order order = orderService.updateOrder(order_id, orderDTO);
            return ResponseEntity.ok(order);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(
            @PathVariable Long id
    ){
        //xoa mem
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Xoa thanh cong order vs id = " + id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}