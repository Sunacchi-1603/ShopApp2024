package com.project.shopApp.responses;

import com.project.shopApp.models.Product;
import lombok.*;

import java.util.List;

@Data //tostring
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListResponse {
    private List<ProductResponse> products;
    private int totalPages;
}
