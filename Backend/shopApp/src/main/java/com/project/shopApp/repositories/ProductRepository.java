package com.project.shopApp.repositories;

import com.project.shopApp.models.Product;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product,Long> {
    //tìm kiếm xem có tên sản phảm này không
    boolean existsByName(String name);

    Page<Product> findAll(Pageable pageable); // phân trang câc sản phẩm
}
