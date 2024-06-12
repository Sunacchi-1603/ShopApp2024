package com.project.shopApp.services;

import com.project.shopApp.dtos.ProductDTO;
import com.project.shopApp.dtos.ProductImageDTO;
import com.project.shopApp.exeptions.DataNotFoundException;
import com.project.shopApp.exeptions.InvalidParamException;
import com.project.shopApp.models.Category;
import com.project.shopApp.models.Product;
import com.project.shopApp.models.ProductImage;
import com.project.shopApp.repositories.CategoryRepository;
import com.project.shopApp.repositories.ProductImageRepository;
import com.project.shopApp.repositories.ProductRepository;
import com.project.shopApp.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService{
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    @Override
    public Product createProduct(ProductDTO productDTO) throws Exception {
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(()->
                new DataNotFoundException(("Cannot find category id:"+ productDTO.getCategoryId())));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long id) throws DataNotFoundException {
        return productRepository.findById(id).orElseThrow(()-> new DataNotFoundException("cannot categoryId " + id));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        // lay danh sach sna pham theo trang(page) va gioi han(limit)
        return productRepository.findAll(pageRequest).map(product ->
                ProductResponse.fromProduct(product)  // hàm fromProduct đã tạo ở bên response.
        );
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws DataNotFoundException {
        Product existingProduct = getProductById(id);
        if(existingProduct != null){
            // copy cacs thuoc tinh tu dto -> product
            // co the su dung modelmapper
            Category existingCategory = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(()->
                    new DataNotFoundException(("Cannot find category id:"+ productDTO.getCategoryId())));
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception {
        Product existingProduct = productRepository.findById(productId).orElseThrow(
                ()->new DataNotFoundException("Cannot find with id:" + productImageDTO.getProductId()));
        ProductImage productImage = ProductImage.builder() // đây mới là cái chính đề lưu vao db
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        // ko cho insert quá 5 ảnh 1 sp
        int size = productImageRepository.findByProductId(existingProduct.getId()).size();
        if(size >= ProductImage.MAXIMUM_IMAGE_PER_PRODUCT){
            throw new InvalidParamException("number of image >= 5");

        }        return productImageRepository.save(productImage);
    }
}
