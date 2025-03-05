package com.project.shopapp.controller;
import com.project.shopapp.dto.request.UserDTO;
import com.project.shopapp.dto.request.UserLoginDTO;
import com.project.shopapp.dto.response.LoginResponse;
import com.project.shopapp.dto.response.RegisterResponse;
import com.project.shopapp.dto.response.ResponseData;
import com.project.shopapp.dto.response.ResponseError;
import com.project.shopapp.model.User;
import com.project.shopapp.service.impl.UserService;
import com.project.shopapp.component.LocalizationUtils;
import com.project.shopapp.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserService userService;
    private final LocalizationUtils localizationUtils;


    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> createUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("Request add user = {}", userDTO.getFullName());
        try{
            User user = userService.createUser(userDTO);
            return ResponseEntity.ok(RegisterResponse.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY))
                            .user(user)
                    .       build());
        }  catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return ResponseEntity.badRequest().body(
                    RegisterResponse.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH))
                            .build()
            );
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserLoginDTO userLogin) {
        // Kiểm tra thông tin đăng nhập và sinh token
        try {
            String token = userService.login(userLogin.getPhoneNumber(), userLogin.getPassword());
            return ResponseEntity.ok(LoginResponse.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                            .token(token)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_FAILED, e.getMessage()))
                            .build()
            );
        }
    }
}
