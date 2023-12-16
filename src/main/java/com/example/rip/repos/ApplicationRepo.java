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
    List<Application> findAllByFormationTimeAfter(LocalDateTime dateFrom);
    List<Application> findAllByCreatorUser_IdAndFormationTimeAfter(Integer id, LocalDateTime dateFrom);
    Optional<Application> findByCreatorUser_UsernameAndStatus(String username, ApplicationStatus status);
}
