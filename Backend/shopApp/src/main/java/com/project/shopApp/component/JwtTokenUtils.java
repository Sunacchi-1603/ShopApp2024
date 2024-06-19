package com.project.shopApp.component;

import com.project.shopApp.exeptions.InvalidParamException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
// để trả về 1 chuỗi string token
//Class JwtTokenUtil này cung cấp các phương thức tiện ích để tạo và xác thực JWT,
// đảm bảo rằng các thông tin trong token là hợp lệ và chưa hết hạn.
public class JwtTokenUtils {
   @Value("${jwt.expiration}")
    private Long expiration; // thời gian token có tác dụng lưu vào biến môi trường
    @Value("${jwt.secretKey}")
    private String secretKey;
    //Hàm tạo ra token
    public String generrateToken(com.project.shopApp.models.User user) throws InvalidParamException {
        // thuộc tính -> claims
        //mã hoá
        Map<String, Object> claims = new HashMap<>();
        claims.put("phoneNumber",user.getPhoneNumber());
        //this.generateSecrietKey();
        try{
            //chuyển đổi
            String token = Jwts.builder()
                    .setClaims(claims)  //làm thế nào để trích xuất cai claim này ra
                    .subject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignInkey(), SignatureAlgorithm.HS256) // câu hỏi bảo mật
                    .compact();
            return  token;
        }catch (Exception e){
           // System.out.println("Cannot create jwt token, error : " + e.getMessage());
            throw  new InvalidParamException("Cannot create jwt token, error : " + e.getMessage());
            //Ukreturn null;
        }
    }
    //Mã hóa khóa bí mật để sử dụng cho việc ký và kiểm tra token.
    private Key getSignInkey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey); //Decoders.BASE64.decode("4nyx7QMPld7tFW8hYCL8gK+AY2y0RIWFIKyhtIgtDi4=")
        return Keys.hmacShaKeyFor(bytes);
    }
    private String generateSecrietKey(){ // hàm này lúc trước là để sinh  secretKey: 4nyx7QMPld7tFW8hYCL8gK+AY2y0RIWFIKyhtIgtDi4=
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);
        String secretKey = Encoders.BASE64.encode(keyBytes);
        return secretKey;
    }
    // hàm trích xuất giá trị
    private Claims extractAllClaims(String token){ // lấy ra tất cả các claims
        return Jwts.parser()
                .setSigningKey(getSignInkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public  <T> T extractClaim(String token, Function<Claims,T> claimsResolver){ // lấy ra claims theo yêu cầu
        final Claims claims =  this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    // check expiration
    private boolean isTokenExpired(String token){ // kiểm tra ngày đó có nằm trước ngày quá hạn hay không
        Date expirationDate = this.extractClaim(token,Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public String extractPhoneNumber(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        String phoneNumber = extractPhoneNumber(token);
        return (phoneNumber.equals(userDetails.getUsername()))
                && !isTokenExpired(token);
    }
}
