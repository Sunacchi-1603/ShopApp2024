package com.project.shopApp.controller;

import com.project.shopApp.dtos.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @PostMapping
    public ResponseEntity<?> insertOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult result

    ){
        try {
            if(result.hasErrors()){
                List<String> errormessage = result.getFieldErrors()
                        .stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errormessage);
            }
            return ResponseEntity.ok("insert order successfully");
        }catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
        }    }
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId){
        try{
            return ResponseEntity.ok("lay user thanh cong");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable Long order_id,
            @RequestBody OrderDTO orderDTO
    ){
        return ResponseEntity.ok("Cap nhat order thanh cong");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(
            @PathVariable Long id
    ){
        //xoa mem
        return ResponseEntity.ok("Xoa thanh cong");
    }
}