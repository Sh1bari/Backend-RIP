package com.example.rip.models.entities;

import com.example.rip.models.enums.RecordState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", unique = true)
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String patronymic;

    private String userGroup;

    @Enumerated(EnumType.STRING)
    private RecordState recordState;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"))
    private List<Role> roles;


}
