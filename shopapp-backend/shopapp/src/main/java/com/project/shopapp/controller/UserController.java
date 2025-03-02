package com.project.shopapp.controller;
import com.project.shopapp.dto.request.UserDTO;
import com.project.shopapp.dto.request.UserLoginDTO;
import com.project.shopapp.dto.response.ResponseData;
import com.project.shopapp.dto.response.ResponseError;
import com.project.shopapp.model.User;
import com.project.shopapp.service.impl.UserService;
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

    @PostMapping("/register")
    public ResponseData<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("Request add user = {}", userDTO.getFullName());
        try{
            User user = userService.createUser(userDTO);
            return new ResponseData<>(HttpStatus.CREATED.value(), "user add successfully", user);
        }  catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLogin) {
        // Kiểm tra thông tin đăng nhập và sinh token
        try {
            String token = userService.login(userLogin.getPhoneNumber(), userLogin.getPassword());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
