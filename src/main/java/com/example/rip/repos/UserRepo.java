package com.example.rip.repos;

import com.example.rip.models.entities.User;
import lombok.*;
import org.springframework.data.repository.CrudRepository;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface UserRepo extends CrudRepository<User, Integer> {
    // Можете добавить дополнительные методы, если это необходимо
}