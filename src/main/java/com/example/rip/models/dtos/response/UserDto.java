package com.example.rip.models.dtos.response;


import com.example.rip.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String token;

    public static UserDto mapFromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .build();
    }
}
