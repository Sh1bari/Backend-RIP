package com.example.rip.sheduler;

import com.example.rip.models.entities.Event;
import com.example.rip.models.enums.EventState;
import com.example.rip.repos.EventRepo;
import lombok.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Component
@RequiredArgsConstructor
public class ApplicationScheduler {

    private final EventRepo eventRepo;
    @Scheduled(cron = "5 * * * * *") // every 5-th second of each minute
    public void setScheduler() {
        List<Event> events = eventRepo.findAllByStateAndEventTimeBefore(EventState.ACTIVE, LocalDateTime.now());
        events.forEach(o->{
            o.setArchiveTime(LocalDateTime.now());
            o.setState(EventState.ARCHIVED);
        });
        eventRepo.saveAll(events);
    }
}
