package com.example.rip.services;

import com.example.rip.exceptions.EventNotFoundException;
import com.example.rip.models.dtos.response.EventRes;
import com.example.rip.models.entities.Event;
import com.example.rip.models.enums.EventState;
import com.example.rip.repos.EventRepo;
import com.example.rip.repos.FileRepo;
import lombok.*;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class EventService {

    private final FileRepo fileRepo;
    private final EventRepo eventRepo;
    private final MinioService minioService;
    private final int limit = 30;

    public EventRes getEventById(Integer id){
        Event event = eventRepo.findById(id)
                .orElseThrow(()-> new EventNotFoundException(id));
        return EventRes.mapFromEntity(event);
    }
    public EventRes deleteEventById(Integer id){
        /*Event event = eventRepo.findById(id)
                .orElseThrow(()-> new EventNotFoundException(id));
        event.setState(EventState.DELETED);
        eventRepo.save(event);*/
        eventRepo.updateStateToDeleted(id);
        Event event = eventRepo.findById(id)
                .orElseThrow(()-> new EventNotFoundException(id));
        return EventRes.mapFromEntity(event);
    }

    public Page<EventRes> getEventsByPageFiltered(String name, EventState status,  Integer page){
        Page<EventRes> res = eventRepo.findAllByNameContainsIgnoreCaseAndState(name,status, PageRequest.of(page, limit))
                .map(EventRes::mapFromEntity);
        return res;
    }

    public byte[] getFileByPath(String path){
        InputStream in = minioService.getFile(path);
        byte[] res = null;
        try {
            res = IOUtils.toByteArray(in);
        } catch (IOException e) {
            // Обработка ошибки, если не удается прочитать изображение
            e.printStackTrace();
        }
        return res;
    }
}
