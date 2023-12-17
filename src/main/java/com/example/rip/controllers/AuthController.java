package com.example.rip.controllers;

import com.example.rip.exceptions.AppError;
import com.example.rip.exceptions.user.UserNotFoundException;
import com.example.rip.models.dtos.RegistrationUserDto;
import com.example.rip.models.dtos.response.JwtRequest;
import com.example.rip.models.dtos.response.JwtResponse;
import com.example.rip.models.dtos.response.UserDto;
import com.example.rip.models.entities.User;
import com.example.rip.repos.UserRepo;
import com.example.rip.services.RedisService;
import com.example.rip.services.security.SecurityAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "API аутентификации", description = "Контроллер для работы с аутентификацией")
public class AuthController {
    private final SecurityAuthService authService;
    private final RedisService redisService;

    @GetMapping("/redis/{key}")
    public String check(@PathVariable String key){
        return redisService.getFromRedis(key);
    }

    @Operation(summary = "Логин")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtResponse.class))
                    }),
            @ApiResponse(responseCode = "401", description = "Incorrect login or password",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AppError.class))
                    }),
            @ApiResponse(responseCode = "403", description = "User has been banned",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AppError.class))
                    })
    })
    @PostMapping("/logIn")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @Operation(summary = "Выход")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно вышел")
    })
    @PostMapping("/logOut")
    @Secured("ROLE_USER")
    public ResponseEntity<?> logout(Principal principal) {
        redisService.saveToRedis(principal.getName(), "OUT");
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Регистрация")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная регистрация",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Password mismatch or user exists",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AppError.class))
                    })
    })
    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

    @Operation(summary = "Обновление jwt токена")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtResponse.class))
                    }),
            @ApiResponse(responseCode = "401", description = "Token timeout",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AppError.class))
                    }),
            @ApiResponse(responseCode = "403", description = "User has been banned",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AppError.class))
                    })
    })
    @Secured("ROLE_USER")
    @PostMapping("/reset-token")
    public ResponseEntity<?> resetToken(Principal principal) {
        return authService.resetToken(principal.getName());
    }
    
}