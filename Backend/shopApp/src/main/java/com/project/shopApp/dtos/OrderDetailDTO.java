package com.project.shopApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopApp.models.Order;
import com.project.shopApp.models.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value = 1,message = "orderId > 0")
    private Long orderId;
    @JsonProperty("product_id")
    @Min(value = 1,message = "productId > 0")
    private Long productId;
    @Min(value = 0,message = "prive >= 0")
    private Float price;
    @JsonProperty("number_of_products")
    private int numberOfProduct;
    @JsonProperty("total_money")
    @Min(value = 0,message = "total money >= 0")
    private int totalMoney;
    private String color;
}
