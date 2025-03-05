package com.project.shopapp.controller;
import com.github.javafaker.Faker;
import com.project.shopapp.component.LocalizationUtils;
import com.project.shopapp.dto.request.ProductDTO;
import com.project.shopapp.dto.request.ProductImageDTO;
import com.project.shopapp.dto.response.ProductDetailResponse;
import com.project.shopapp.dto.response.ProductListResponse;
import com.project.shopapp.dto.response.ResponseData;
import com.project.shopapp.dto.response.ResponseError;
import com.project.shopapp.model.Product;
import com.project.shopapp.model.ProductImage;
import com.project.shopapp.service.impl.ProductService;
import com.project.shopapp.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;
    private final LocalizationUtils localizationUtils;

    @PostMapping("")
    public ResponseData<?> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        try {
            log.info("Request create product = {}", productDTO.getName());
            Product newProduct = productService.createProduct(productDTO);
            return new ResponseData<>(HttpStatus.OK.value(), "create new product successfully", newProduct);
        } catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "create new product fail");
        }
    }
    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@PathVariable("id") Long productId,
                                          @RequestParam("files") List<MultipartFile> files) {
        try {
            ProductDetailResponse existingProduct = productService.getProduct(productId);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            if(files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
                return ResponseEntity.badRequest().body(localizationUtils.getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_MAX_5));
            }
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if(file.getSize() == 0) {
                    continue;
                }
                // Kiểm tra kích thước file và định dạng
                if(file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(localizationUtils.getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_FILE_LARGE));
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(localizationUtils.getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_FILE_MUST_BE_IMAGE));
                }
                // Lưu file và cập nhật thumbnail trong DTO
                String filename = storeFile(file); // Thay thế hàm này với code của bạn để lưu file
                //lưu vào đối tượng product trong DB
                ProductImage productImage = productService.createProductImage(
                        existingProduct.getId(),
                        ProductImageDTO.builder()
                                .imageUrl(filename)
                                .build()
                );
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName) {
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
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
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @PutMapping("/{proId}")
    public ResponseData<?> updateProducts(@Valid @PathVariable("proId") Long id, @Valid @RequestBody ProductDTO productDTO) {
        try {
            log.info("Request update product = {}", productDTO.getName());
            productService.updateProduct(id, productDTO);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "update product successfully with id = " + id, productDTO);
        } catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "update product fail");
        }
    }

    @GetMapping("")
    public ResponseData<ProductListResponse> getProducts(@RequestParam(defaultValue = "0", required = false) int page,
                                                         @RequestParam(defaultValue = "1", required = false) int limit) {
        try {
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get list of products successfully", productService.getAllProducts(page, limit));
        }catch (Exception e) {
            log.error("errorMessage = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "get list products fail");
        }
    }

    @GetMapping("/{proId}")
    public ResponseData<ProductDetailResponse> getProductById(@Valid @PathVariable("proId") Long id) {
        try {
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "get product successfully with id = " + id, productService.getProduct(id));
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

    @PostMapping("/generateFakeProducts")
    public ResponseData<?> generateFakeProducts() {
        Faker faker = new Faker();
        for(int i = 0; i < 70; i++) {
            String productName = faker.commerce().productName();
            if(productService.existsByName(productName)) {
                continue;
            }
            ProductDTO newProduct = ProductDTO.builder()
                    .name(productName)
                    .price((float) faker.number().numberBetween(20, 1000))
                    .description(faker.lorem().sentence())
                    .thumbnail(faker.lorem().sentence())
                    .categoryId((long) faker.number().numberBetween(1, 4))
                    .build();
            try {
                productService.createProduct(newProduct);
            }catch (Exception e) {
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), "generate data products fail");
            }
        }
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "generate data products successfully");
    }
}
