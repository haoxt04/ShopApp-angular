package com.project.shopapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetail {
    @JsonProperty("order_id")
    @Min(value = 1, message = "id must be greater than 0")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "id must be greater than 0")
    private Long productId;

    @Min(value = 0, message = "price must be greater than 0")
    private float price;

    @JsonProperty("number_of_products")
    @Min(value = 0, message = "number of product must be greater than 0")
    private int numberOfProducts;

    @JsonProperty("total_money")
    @Min(value = 0, message = "total money must be greater than 0")
    private float totalMoney;

    private String color;
}
