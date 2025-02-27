package com.project.shopapp.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;

    private String note;

    private Date orderDate;

    private String status;

    private float totalMoney;

    private String shippingMethod;

    private String shippingAddress;

    private LocalDate shippingDate;

    private String paymentMethod;
}
