package com.example.rip.repos;

import com.example.rip.models.entities.Application;
import com.example.rip.models.enums.ApplicationStatus;
import lombok.*;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface ApplicationRepo extends CrudRepository<Application, Integer> {

    Optional<Application> findByCreatorUser_IdAndStatus(Integer creatorUser_id, ApplicationStatus status);
}
