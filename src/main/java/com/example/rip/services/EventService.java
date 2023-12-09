package com.example.rip.services;

import com.example.rip.exceptions.event.EventNotFoundException;
import com.example.rip.models.dtos.request.EventCreateReq;
import com.example.rip.models.dtos.response.EventRes;
import com.example.rip.models.entities.Event;
import com.example.rip.models.enums.EventState;
import com.example.rip.repos.EventRepo;
import com.example.rip.repos.FileRepo;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;
    private final MinioService minioService;
    private final FileRepo fileRepo;

    public EventRes getEventById(Integer id){
        Event event = eventRepo.findById(id)
                .orElseThrow(()-> new EventNotFoundException(id));
        return EventRes.mapFromEntity(event);
    }
    public EventRes deleteEventById(Integer id){

        eventRepo.updateStateToDeleted(id);
        Event event = eventRepo.findById(id)
                .orElseThrow(()-> new EventNotFoundException(id));
        minioService.deleteFile("rip", event.getFile().getPath());
        event.getFile().setPath("notFound.png");
        eventRepo.save(event);
        return EventRes.mapFromEntity(event);
    }

    public Page<EventRes> getEventsByPageFiltered(String name, EventState status,  Integer page, Integer limit){
        Page<EventRes> res = eventRepo.findAllByNameContainsIgnoreCaseAndState(name,status, PageRequest.of(page, limit))
                .map(EventRes::mapFromEntity);
        return res;
    }

    public Event addEvent (EventCreateReq req){
        Event event = req.mapToEntity();
        event.getFile().setEvent(event);
        eventRepo.save(event);
        return event;
    }

    public Event updateEvent (Integer id, EventCreateReq req){
        Event event = eventRepo.findById(id)
                        .orElseThrow(()-> new EventNotFoundException(id));
        event.setEventTime(req.getEventTime());
        event.setName(req.getName());
        event.setTickets(req.getTickets());
        event.setDescription(req.getDescription());
        eventRepo.save(event);
        return event;
    }

}
