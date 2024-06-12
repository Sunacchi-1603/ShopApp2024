package com.project.shopApp.repositories;

import com.project.shopApp.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    // đây là cấu trúc tên mà java spring nó sẽ hiểu để tìm ra order
    List<OrderDetail> findByOrderId(Long orderid);
}
