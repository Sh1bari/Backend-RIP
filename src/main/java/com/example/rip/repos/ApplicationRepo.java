package com.example.rip.repos;

import com.example.rip.models.entities.Application;
import lombok.*;
import org.springframework.data.repository.CrudRepository;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface ApplicationRepo extends CrudRepository<Application, Integer> {
}
