package com.project.shopapp.service;

import com.project.shopapp.dto.request.UserDTO;
import com.project.shopapp.model.User;

public interface IUserService {

    User createUser(UserDTO user);

    String login(String phoneNumber, String password) throws Exception;
}
