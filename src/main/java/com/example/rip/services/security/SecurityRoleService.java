package com.example.rip.services.security;


import com.example.rip.models.entities.Role;
import com.example.rip.repos.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityRoleService {
    private final RoleRepo roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
    public Role getAdminRole() {
        return roleRepository.findByName("ROLE_ADMIN").get();
    }
}
