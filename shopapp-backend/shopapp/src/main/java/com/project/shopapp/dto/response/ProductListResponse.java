package com.project.shopapp.dto.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class ProductListResponse implements Serializable {
    private List<ProductDetailResponse> products;
    private int totalPages;
}
