package com.project.shopapp.service.impl;

import com.project.shopapp.model.Role;
import com.project.shopapp.repository.RoleRepository;
import com.project.shopapp.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
