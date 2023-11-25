package com.example.rip.repos;

import com.example.rip.models.entities.Role;
import lombok.*;
import org.springframework.data.repository.CrudRepository;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface RoleRepo extends CrudRepository<Role, Integer> {
    // Можете добавить дополнительные методы, если это необходимо
}
