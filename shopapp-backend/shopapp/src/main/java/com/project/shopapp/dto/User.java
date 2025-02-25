package com.project.shopapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.utils.PhoneNumber;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Builder
public class User {
    @JsonProperty("fullname")
    @NotNull(message = "full name of user must be not null")
    private String fullName;

    @JsonProperty("phone_number")
    @PhoneNumber(message = "phone invalid format")
    private String phoneNumber;

    @NotNull(message = "address of user must be not null")
    private String address;

    @NotNull(message = "password must be not null")
    private String password;

    @JsonProperty("retype_password")
    @NotNull(message = "password must be not null")
    private String retypePassword;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;

    @NotNull(message = "Role ID is required")
    @JsonProperty("role_id")
    private Long roleId;
}
