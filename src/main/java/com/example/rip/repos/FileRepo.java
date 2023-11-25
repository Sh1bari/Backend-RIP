package com.example.rip.repos;

import com.example.rip.models.entities.File;
import lombok.*;
import org.springframework.data.repository.CrudRepository;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface FileRepo extends CrudRepository<File, Integer> {
    // Можете добавить дополнительные методы, если это необходимо
}
