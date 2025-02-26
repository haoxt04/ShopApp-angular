package com.project.shopapp.dto.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductImageDTO {
    @JsonProperty("product_id")
    @Min(value = 1, message = "product id must be greater than 0")
    private Long productId;

    @Column(name = "image_url")
    private String imageUrl;
}
