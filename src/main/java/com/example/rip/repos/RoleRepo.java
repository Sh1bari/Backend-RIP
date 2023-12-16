package com.example.rip.repos;

import com.example.rip.models.entities.Role;
import lombok.*;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface RoleRepo extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
