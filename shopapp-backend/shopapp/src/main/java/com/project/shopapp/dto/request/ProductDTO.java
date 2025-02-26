package com.project.shopapp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class ProductDTO {
    @NotNull(message = "product name must be not null")
    private String name;

    @Min(value = 0, message = "product price must be greater than or equal to 0")
    private float price;

    private String thumbnail;

    private String description;

    @JsonProperty("category_id")
    private Long categoryId;

    private List<MultipartFile> files;

}
