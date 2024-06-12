package com.project.shopApp.repositories;

import com.project.shopApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByPhoneNumber(String phoneNumber); // kiểm tra xem phone number này có tồn tại hay không
    Optional<User>  findByPhoneNumber(String phoneNumber); //kiểm tra kết quả null hoặc khác null
    // SELECT * FROM users WHERE phoneNumber = ?

}
