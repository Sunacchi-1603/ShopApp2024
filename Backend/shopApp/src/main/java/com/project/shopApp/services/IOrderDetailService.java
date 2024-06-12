package com.project.shopApp.services;

import com.project.shopApp.dtos.OrderDetailDTO;
import com.project.shopApp.exeptions.DataNotFoundException;
import com.project.shopApp.models.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO newOrderDetail) throws Exception;
    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO newOrderDetailData)
            throws DataNotFoundException;
    void deleteById(Long id) throws DataNotFoundException;
    List<OrderDetail> findByOrderId(Long orderId) throws DataNotFoundException;
}
