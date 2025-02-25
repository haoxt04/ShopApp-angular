package com.project.shopapp.dto.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.utils.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDTO {
    @JsonProperty("user_id")
    @Min(value = 1, message = "userId must be greater than 0")
    private Long userId;

    @JsonProperty("fullname")
    @NotNull(message = "full name must be not null")
    private String fullName;

    @Email(message = "email invalid format")
    private String email;

    @JsonProperty("phone_number")
    @PhoneNumber(message = "phone invalid format")
    private String phoneNumber;

    private String address;

    private String note;

    @JsonProperty("total_money")
    @Min(value = 0, message = "total money must be greater than 0")
    private float totalMoney;

    @JsonProperty("shipping_method")
    @NotNull(message = "shipping method must be not null")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    @NotNull(message = "shipping address must be not null")
    private String shippingAddress;

    @JsonProperty("payment_method")
    @NotNull(message = "payment method must be not null")
    private String paymentMethod;


}
