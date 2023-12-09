package com.example.rip.repos;

import com.example.rip.models.entities.Event;
import com.example.rip.models.enums.EventState;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface EventRepo extends CrudRepository<Event, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE Event SET state = 'DELETED' WHERE id = ?1", nativeQuery = true)
    void updateStateToDeleted(Integer id);

    List<Event> findAllByStateAndEventTimeBefore(EventState state, LocalDateTime eventTime);
    Page<Event> findAllByNameContainsIgnoreCaseAndState(String name, EventState status, Pageable pageable);
}
