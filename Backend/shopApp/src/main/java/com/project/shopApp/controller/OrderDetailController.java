package com.project.shopApp.controller;

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
    @GetMapping("/{id}")
    public 
}
