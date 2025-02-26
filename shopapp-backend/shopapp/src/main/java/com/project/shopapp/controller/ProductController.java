package com.project.shopapp.controller;
import com.project.shopapp.dto.request.ProductDTO;
import com.project.shopapp.dto.response.ResponseData;
import com.project.shopapp.dto.response.ResponseError;
import com.project.shopapp.service.impl.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseData<?> createProduct(@Valid @ModelAttribute ProductDTO product) {
        try {
            List<MultipartFile> files = product.getFiles();
            files = files == null ? new ArrayList<MultipartFile>() : files;
            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }
                // Kiểm tra kích thước file và định dạng
                if (file.getSize() > 30 * 1024 * 1024) { // Kích thước > 10MB
                    return new ResponseData<>(HttpStatus.PAYLOAD_TOO_LARGE.value(), "File is too large, max 30MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return new ResponseData<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "File must be an image");
                }
                // Lưu file và cập nhật thumbnail trong DTO
                String filename = storeFile(file); // Thay thế hàm này với code của bạn để lưu file
                //lưu vào đối tượng product trong DB => sẽ làm sau
                //lưu vào bảng product_images
            }
            return new ResponseData<>(HttpStatus.CREATED.value(), "product create successfully");
        } catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "create product fail");
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    @GetMapping("")
    public ResponseData<?> getProducts(@RequestParam(defaultValue = "0", required = false) int page,
                                    @RequestParam(defaultValue = "1", required = false) int limit) {
        try {
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get list of products successfully", productService.getAllProducts(page, limit));
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "get list products fail");
        }
    }

    @GetMapping("/{proId}")
    public ResponseData<?> getProductById(@Valid @PathVariable("proId") Long id) {
        try {
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get product successfully with id = " + id, productService.getProductById(id));
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "get product fail");
        }
    }

    @DeleteMapping("/{proId}")
    public ResponseData<?> deleteProduct(@Valid @PathVariable("proId") Long id) {
        try {
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "delete product successfully with id = " + id);
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "delete product fail");
        }
    }
}
