package com.project.shopApp.controller;

import com.project.shopApp.dtos.OrderDTO;
import com.project.shopApp.dtos.OrderDetailDTO;
import com.project.shopApp.exeptions.DataNotFoundException;
import com.project.shopApp.models.OrderDetail;
import com.project.shopApp.responses.OrderDetailResponse;
import com.project.shopApp.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orderDetail")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO,
            BindingResult result
            ){
        try {
            OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok().body(OrderDetailResponse.fromOrderDetail(newOrderDetail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderDetailResponse>> getOrderDetailByOrderId(@Valid @PathVariable("orderId") Long orderId) throws DataNotFoundException {
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
        List<OrderDetailResponse> orderDetailResponses = orderDetails
                .stream()
                .map(OrderDetailResponse::fromOrderDetail)
                .toList();
        return ResponseEntity.ok(orderDetailResponses);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetailById(@Valid @PathVariable("id") Long id){
        try {
            OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
            return ResponseEntity.ok(orderDetail);
        } catch (DataNotFoundException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable Long order_id,
            @RequestBody OrderDetailDTO orderDetailDTO
    ){
        try {
           OrderDetail orderDetail = orderDetailService.updateOrderDetail(order_id,orderDetailDTO);
            return ResponseEntity.ok(orderDetail);
        } catch (DataNotFoundException e) {
          return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderId(@Valid @PathVariable("id") Long id){
        try {
            orderDetailService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
