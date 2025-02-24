package com.project.shopapp.dto;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
public class Category {
    private Long id;
    @NotEmpty(message = "category's name can not be empty")
    private String name;
}
