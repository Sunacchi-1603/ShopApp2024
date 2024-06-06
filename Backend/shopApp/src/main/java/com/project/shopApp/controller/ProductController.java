package com.project.shopApp.controller;

import com.project.shopApp.dtos.CategoryDTO;
import com.project.shopApp.dtos.ProductDTO;
import jakarta.validation.Valid;
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
public class ProductController {
    @GetMapping("")
    public ResponseEntity<String> getAllProduct() {
        return ResponseEntity.ok("123");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable("id") String productId) {
        return ResponseEntity.ok("id " + productId);
    }
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)  //đê upload và kiểm tra ảnh
    public ResponseEntity<?> createProduct(
            @Valid @ModelAttribute ProductDTO productDTO,
            //@RequestPart("file") MultipartFile file,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            List<MultipartFile> files = productDTO.getFiles();
            files = files == null ? new ArrayList<MultipartFile>() : files;
            //duyet danh sách anh
            for (MultipartFile file : files) {
                    if(file.getSize() == 0){
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

                    //lưu vào đối tượng product trong DB => sẽ làm sau
                    //lưu vào bảng product_images
                }
            return ResponseEntity.ok("Product inser successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //lưu tên file
    private String storeFile(MultipartFile file) throws IOException {
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

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id) {
        return ResponseEntity.ok("This is update product" + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok("This is delete product" + id);
    }
}
