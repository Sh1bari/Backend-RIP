package com.example.rip.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class RegistrationUserDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String patronymic;

    @NotNull
    private String userGroup;
}
