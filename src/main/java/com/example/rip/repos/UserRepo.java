package com.example.rip.repos;

import com.example.rip.models.entities.User;
import lombok.*;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface UserRepo extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}