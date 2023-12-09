package com.example.rip.repos;

import com.example.rip.models.entities.Application;
import com.example.rip.models.enums.ApplicationStatus;
import lombok.*;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface ApplicationRepo extends CrudRepository<Application, Integer> {
    List<Application> findAllByStatusAndFormationTimeAfter(ApplicationStatus status, LocalDateTime dateFrom);
    Optional<Application> findByCreatorUser_IdAndStatus(Integer creatorUser_id, ApplicationStatus status);
}
