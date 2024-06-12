package com.project.shopApp.controller;

import com.project.shopApp.dtos.CategoryDTO;
import com.project.shopApp.dtos.ProductDTO;
import com.project.shopApp.dtos.ProductImageDTO;
import com.project.shopApp.models.Product;
import com.project.shopApp.models.ProductImage;
import com.project.shopApp.responses.ProductListResponse;
import com.project.shopApp.responses.ProductResponse;
import com.project.shopApp.services.ICategoryService;
import com.project.shopApp.services.IProductService;
import com.project.shopApp.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    public final IProductService productService;

    @GetMapping("")
    public ResponseEntity<?> getAllProduct(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        // Tạo ra page request từ thông tin trang và giới hạn
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);
        // lay ra tong so trang
        int totalpage = productPage.getTotalPages();
        List<ProductResponse>  productResponses = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse.builder()
                        .products(productResponses)
                        .totalPages(totalpage)
                .build());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long productId) {
        try {
          Product product = productService.getProductById(productId);
          return ResponseEntity.ok(ProductResponse.fromProduct(product));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("cannot find product");
        }
    }

    @PostMapping(value = "")  //đê upload và kiểm tra ảnh
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            //@ModelAttribute("files") List<MultipartFile> files,
            //@RequestPart("file") MultipartFile file,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Product newProduct = productService.createProduct(productDTO);

            return ResponseEntity.ok("Product inser successfully" + newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @ModelAttribute("files") List<MultipartFile> files,
            @PathVariable("id") Long productId
    ) {
        try {
            Product existingProduct = productService.getProductById(productId);
            List<ProductImage> productImages = new ArrayList<>();
            files = files == null ? new ArrayList<MultipartFile>() : files; // tránh bị lỗi không đáng có
            if(files.size() > ProductImage.MAXIMUM_IMAGE_PER_PRODUCT){
                return ResponseEntity.badRequest().body("you can only upload maximum 5 image");

            }            //duyet danh sách anh
            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }
                // kiểm tra kích thức file và định dạng
                if (file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("image maximum is 10m");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("file must be image");
                }
                // Lưu file và cập nhật thumbnail trong DTO
                String filename = storeFile(file); // Thay thế hàm này với code của bạn để lưu file
                ProductImage newProductImage = productService.createProductImage(existingProduct.getId(), ProductImageDTO.builder()
                        // cái  ProductImageDTO dùng để chứa đường dẫn ảnh
                        .imageUrl(filename)
                        .build());
                //lưu vào đối tượng product trong DB => sẽ làm sau
                //lưu vào bảng product_images
                productImages.add(newProductImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //lưu tên file
    private String storeFile(MultipartFile file) throws IOException {
        if(!isImageFile(file) || file.getOriginalFilename() == null){ // kiểm tra xem là ảnh hay không
            throw new IOException("Invalid id image format");
        }
        //Làm sạch đường dẫn
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        // Thêm uuid vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        java.nio.file.Path upLoasDir = Paths.get("uploads");
        //kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(upLoasDir)) {
            Files.createDirectories(upLoasDir);

        }
        //Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(upLoasDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    // kiểm tra xem phải lad ảnh hay không
    private boolean isImageFile(MultipartFile file){
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @Valid @PathVariable Long id,
            @RequestBody ProductDTO productDTO
            ) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(String.format("Product with id = %d deleted successfully", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
