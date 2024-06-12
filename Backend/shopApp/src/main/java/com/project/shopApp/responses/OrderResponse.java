package com.project.shopApp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopApp.models.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderResponse extends BaseResponse{
    private Long id;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("fullname")
    private String fullname;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String address;
    private String note;
    @JsonProperty("order_date")
    private LocalDateTime orderDate;
    private String status;
    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("shipping_date")
    private LocalDate shippingDate;

    @JsonProperty("tracking_number")
    private String trackingNumber;
    @JsonProperty("active")
    private Boolean active;
}
