package com.project.shopApp.configurations;

import com.project.shopApp.models.User;
import com.project.shopApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
//Đoạn mã này cấu hình Spring Security để xử lý xác thực người dùng, đảm bảo rằng mật khẩu được mã hóa
//  và người dùng được xác thực đúng cách khi họ đăng nhập vào hệ thống.
public class SecuriryConfig {
    private final UserRepository userRepository;
    // user detail object
    @Bean
    public UserDetailsService userDetailsService(){ //tải thông tin chi tiết về người dùng dựa trên số điện thoại.
        return phoneNumber ->{
            User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                    .orElseThrow(()->
                           new UsernameNotFoundException("Cannot find user with phone number"));
            return existingUser;
        };
    }
    // đối tượng mã hoá mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // thuật toán mã hoá
    }
    //để mã hóa và xác thực mật khẩu của họ. Điều này giúp trong quá trình xác thực người dùng khi họ cố gắng truy cập vào ứng dụng.
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); //được sử dụng để xác thực người dùng từ cơ sở dữ liệu.
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { //  quản lý quá trình xác thực.
        return config.getAuthenticationManager();
    }

}
