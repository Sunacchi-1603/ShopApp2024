package com.project.shopApp.services;

import com.project.shopApp.dtos.OrderDTO;
import com.project.shopApp.exeptions.DataNotFoundException;
import com.project.shopApp.models.Order;

import java.util.List;

public interface IOrderRervice {
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order getOrder(Long id) throws DataNotFoundException;
    Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    void deleteOrder(Long id) throws Exception;
    List<Order> findByUserId(Long userId);
}
