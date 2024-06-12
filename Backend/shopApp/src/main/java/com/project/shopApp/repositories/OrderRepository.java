package com.project.shopApp.repositories;


import com.project.shopApp.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    // tìm kiếm số order của 1 userId
    List<Order> findByUserId(Long userId);
}
