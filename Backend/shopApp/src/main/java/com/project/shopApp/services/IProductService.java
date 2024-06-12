package com.project.shopApp.services;

import com.project.shopApp.dtos.ProductDTO;
import com.project.shopApp.dtos.ProductImageDTO;
import com.project.shopApp.exeptions.DataNotFoundException;
import com.project.shopApp.models.Product;
import com.project.shopApp.models.ProductImage;
import com.project.shopApp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;
    Product getProductById(long id) throws Exception;
    //phan trang
   Page<ProductResponse> getAllProducts(PageRequest pageRequest);
    Product updateProduct(long id, ProductDTO productDTO) throws DataNotFoundException;
    void deleteProduct(long id);

    boolean existsByName(String name);

    ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception;
}
