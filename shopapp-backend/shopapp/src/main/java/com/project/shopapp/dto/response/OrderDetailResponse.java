package com.project.shopapp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private Long id;

    private Long orderId;

    private Long productId;

    private Float price;

    private int numberOfProducts;

    private Float totalMoney;

    private String color;
}
