package com.project.shopApp.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopApp.models.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data //tostring
@Getter
@Setter
@AllArgsConstructor //ham khoi tao day du tham so
@NoArgsConstructor // ham khoi tao khong tham so
@Builder // thuong dung de chuyen doi gia tri tu class nay sang class khac
public class ProductImageDTO {
    @JsonProperty("product_id")
    @Min(value = 1,message = "product id getter equal than 1")
    private Long productId;
    @JsonProperty("image_url")
    @Size(min = 5,max = 300,message = "image url must be between 3 - 300 character")
    private String imageUrl;
}
