package com.example.rip.repos;

import com.example.rip.models.entities.Event;
import com.example.rip.models.enums.EventState;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface EventRepo extends CrudRepository<Event, Integer> {
    Page<Event> findAllByNameContainsIgnoreCaseAndState(String name, EventState status, Pageable pageable);
}
