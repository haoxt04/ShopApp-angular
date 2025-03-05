package com.project.shopapp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.model.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("user")
    private User user;
}
