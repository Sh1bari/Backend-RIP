package com.example.rip.services.security;

import com.example.rip.exceptions.user.DeletedUserException;
import com.example.rip.models.dtos.RegistrationUserDto;
import com.example.rip.models.entities.User;
import com.example.rip.models.enums.RecordState;
import com.example.rip.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService {

    private final UserRepo userRepository;
    private final SecurityRoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' is not found", username)
        ));
        if (user.getRecordState().equals(RecordState.DELETED)) {
            throw new DeletedUserException(username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public User createNewUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setUsername(registrationUserDto.getUsername());
        user.setRoles(List.of(roleService.getUserRole()));
        user.setRecordState(RecordState.ACTIVE);
        return userRepository.save(user);
    }
}
