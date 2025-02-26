package com.project.shopapp.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailResponse implements Serializable {
    private Long id;

    private String name;

    private float price;

    private String thumbnail;

    private String description;

    private Long categoryId;
}
