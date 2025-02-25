package com.project.shopapp.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Builder
public class CategoryDTO {
    @Min(value = 1, message = "id must be greater than 0")
    private Long id;
    @NotEmpty(message = "category name must be not empty")
    private String name;
}
