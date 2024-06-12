package com.project.shopApp.responses;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopApp.models.Product;
import com.project.shopApp.models.Product;
import lombok.*;

@Data //tostring
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse {
    private  String name;
    private  Float price;
    private  String thumnail;
    private  String description;
    @JsonProperty("category_id")
    private Long categoryId;

    // thêm phần này để giảm trung lặp code vì có nhiều đoạn giống nhau như thế này
    public static ProductResponse fromProduct(Product product){
        ProductResponse productResponse =
                ProductResponse.builder()
                        .name(product.getName())
                        .price(product.getPrice())
                        .thumnail(product.getThumbnail())
                        .description(product.getDescription())
                        .categoryId(product.getCategory().getId())
                        .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}

