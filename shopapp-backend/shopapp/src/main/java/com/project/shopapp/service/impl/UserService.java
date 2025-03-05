package com.project.shopapp.service.impl;

import com.project.shopapp.component.JwtTokenUtils;
import com.project.shopapp.dto.request.UserDTO;
import com.project.shopapp.exception.PermissionDenyException;
import com.project.shopapp.exception.ResourceNotFoundException;
import com.project.shopapp.model.Role;
import com.project.shopapp.model.User;
import com.project.shopapp.repository.RoleRepository;
import com.project.shopapp.repository.UserRepository;
import com.project.shopapp.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public User createUser(UserDTO userDTO) {
        // register user
        String phoneNumber = userDTO.getPhoneNumber();
        // kiểm tra số điện thoại đã tồn tại chưa
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("phone number already exists");
        }
        Role role = roleRepository.findById(userDTO.getRoleId()).orElseThrow(() ->
                new ResourceNotFoundException("role id not found"));
        if(role.getName().toUpperCase().equals(Role.ADMIN)) {
            throw new PermissionDenyException("you can not register an admin role");
        }
        //convert from userDTO => user
        User user = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        user.setRole(role);

        // Kiểm tra nếu có accountId, không yêu cầu password
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
        }
        return userRepository.save(user);
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("Invalid phone number or password");
        }
        //return optionalUser.get();      // muốn trả về JWT token
        User existingUser = optionalUser.get();
        // check password
        if(existingUser.getFacebookAccountId() == 0 && existingUser.getGoogleAccountId() == 0) {
            if(!passwordEncoder.matches(password, existingUser.getPassword())) {
                throw new BadCredentialsException("Wrong phone number or password");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(phoneNumber, password, existingUser.getAuthorities());
        // authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
