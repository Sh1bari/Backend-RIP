package com.example.rip.services.security;

import com.example.rip.exceptions.AppError;
import com.example.rip.models.dtos.RegistrationUserDto;
import com.example.rip.models.dtos.response.JwtRequest;
import com.example.rip.models.dtos.response.JwtResponse;
import com.example.rip.models.dtos.response.UserDto;
import com.example.rip.models.entities.User;
import com.example.rip.repos.UserRepo;
import com.example.rip.services.RedisService;
import com.example.rip.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class SecurityAuthService {
    private final SecurityUserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final RedisService redisService;


    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect login or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        redisService.saveToRedis(userDetails.getUsername(), "IN");
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrationUserDto);
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        redisService.saveToRedis(user.getUsername(), "IN");
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), token));
    }

    public ResponseEntity<?> resetToken(String username) {
        UserDetails userDetails = userService.loadUserByUsername(username);
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, username));
    }
}
