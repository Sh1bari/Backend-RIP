package com.example.rip.controllers;

import com.example.rip.models.dtos.request.EventCreateReq;
import com.example.rip.models.dtos.response.ApplicationRes;
import com.example.rip.models.dtos.response.EventAllRes;
import com.example.rip.models.dtos.response.EventRes;
import com.example.rip.models.dtos.response.FileRes;
import com.example.rip.models.entities.Application;
import com.example.rip.models.entities.Event;
import com.example.rip.models.entities.File;
import com.example.rip.models.enums.ApplicationStatus;
import com.example.rip.models.enums.EventState;
import com.example.rip.repos.ApplicationRepo;
import com.example.rip.services.ApplicationService;
import com.example.rip.services.EventService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@RestController
@CrossOrigin
@RequiredArgsConstructor
@Validated
public class EventController {

    private final EventService eventService;
    private final ApplicationRepo applicationRepo;
    private final ApplicationService applicationService;
    private final int id = 1;

    @GetMapping("/events")
    public ResponseEntity<EventAllRes> getEvents(
            @RequestParam(name = "eventStatus", required = false, defaultValue = "ACTIVE") EventState status,
            @RequestParam(name = "eventName", required = false, defaultValue = "") String name,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "limit", required = false, defaultValue = "30") Integer limit){
        Page<EventRes> resPage = eventService.getEventsByPageFiltered(name, status, page, limit);
        Application application = applicationRepo.findByCreatorUser_IdAndStatus(id, ApplicationStatus.DRAFT)
                .orElse(new Application());
        EventAllRes res = new EventAllRes();
        res.setEvents(resPage);
        res.setApplicationId(application.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<EventRes> getEventById(
            @PathVariable(value = "id") @Min(value = 1, message = "Id не может быть меньше 1") Integer id){
        EventRes res = eventService.getEventById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @PostMapping("event/{id}/photo")
    public ResponseEntity<FileRes> updatePhoto(
            @PathVariable(value = "id") @Min(value = 1, message = "Id не может быть меньше 1") Integer id,
            @RequestParam(value = "file", required = true) MultipartFile file){
        FileRes res = eventService.savePhotoByEvent(id, file);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @PostMapping("/events")
    public ResponseEntity<EventRes> addEvent(
            @RequestBody @Valid EventCreateReq req){

        Event res = eventService.addEvent(req);
        return ResponseEntity
                .created(URI.create("/api/event/" + res.getId()))
                .body(EventRes.mapFromEntity(res));
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable(name = "id") Integer id) {
        EventRes res = eventService.deleteEventById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/event/{id}")
    public ResponseEntity<EventRes> updateEvent(
            @PathVariable(value = "id") @Min(value = 1, message = "Id не может быть меньше 1") Integer id,
            @RequestBody @Valid EventCreateReq req){

        Event res = eventService.updateEvent(id, req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EventRes.mapFromEntity(res));
    }

    @PostMapping("/application/{id}/event/{eId}")
    public ResponseEntity<ApplicationRes> addEventToApplication(
            @PathVariable(value = "id") @Min(value = 1, message = "Id не может быть меньше 1") Integer id,
            @PathVariable(value = "eId") @Min(value = 1, message = "Id не может быть меньше 1") Integer eId){
        ApplicationRes res = applicationService.addEvent(id, eId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @DeleteMapping("/application/{id}/event/{eId}")
    public ResponseEntity<ApplicationRes> deleteEventFromApplication(
            @PathVariable(value = "id") @Min(value = 1, message = "Id не может быть меньше 1") Integer id,
            @PathVariable(value = "eId") @Min(value = 1, message = "Id не может быть меньше 1") Integer eId){
        ApplicationRes res = applicationService.deleteEvent(id, eId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

}
