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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@RestController
@CrossOrigin
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "API мероприятий", description = "Контроллер для работы с мероприятиями")
public class EventController {

    private final EventService eventService;
    private final ApplicationRepo applicationRepo;
    private final ApplicationService applicationService;

    @Operation(summary = "Список мероприятий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventAllRes.class))
                    })
    })
    @GetMapping("/events")
    public ResponseEntity<EventAllRes> getEvents(
            @RequestParam(name = "eventStatus", required = false, defaultValue = "ACTIVE") EventState status,
            @RequestParam(name = "eventName", required = false, defaultValue = "") String name,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "limit", required = false, defaultValue = "20") Integer limit,
            Principal principal){
        Page<EventRes> resPage = eventService.getEventsByPageFiltered(name, status, page, limit);
        EventAllRes res = new EventAllRes();
        if(principal != null) {
            Application application = applicationRepo.findByCreatorUser_UsernameAndStatus(principal.getName(), ApplicationStatus.DRAFT)
                    .orElse(new Application());
            res.setEvents(resPage);
            res.setApplicationId(application.getId() != null ? application.getId() : 0);
        }else {
            res.setEvents(resPage);
            res.setApplicationId(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Получить мероприятие по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventRes.class))
                    })
    })
    @GetMapping("/event/{id}")
    public ResponseEntity<EventRes> getEventById(
            @PathVariable(value = "id") @Min(value = 1, message = "Id не может быть меньше 1") Integer id,
            Principal principal){
        EventRes res = eventService.getEventById(id);
        if(principal != null) {
            Application application = applicationRepo.findByCreatorUser_UsernameAndStatus(principal.getName(), ApplicationStatus.DRAFT)
                    .orElse(new Application());
            res.setApplicationId(application.getId() != null ? application.getId() : 0);
        }else {
            res.setApplicationId(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Добавить фото к мероприятию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FileRes.class))
                    })
    })
    @PostMapping("event/{id}/photo")
    public ResponseEntity<FileRes> updatePhoto(
            @PathVariable(value = "id") @Min(value = 1, message = "Id не может быть меньше 1") Integer id,
            @RequestParam(value = "file", required = true) MultipartFile file){
        FileRes res = eventService.savePhotoByEvent(id, file);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
    @PostMapping("/init")
    public void addEvents(){
        for(int i = 0; i < 30000; i++){
            eventService.addEvent(new EventCreateReq("event" + i, "", 1, LocalDateTime.now().plusHours(5)));
        }
    }

    @Operation(summary = "Добавить мероприятие")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventRes.class))
                    })
    })
    @PostMapping("/events")
    public ResponseEntity<EventRes> addEvent(
            @RequestBody @Valid EventCreateReq req){

        Event res = eventService.addEvent(req);
        return ResponseEntity
                .created(URI.create("/api/event/" + res.getId()))
                .body(EventRes.mapFromEntity(res));
    }

    @Operation(summary = "Удалить мероприятие")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "")
    })
    @DeleteMapping("/event/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable(name = "id") Integer id) {
        EventRes res = eventService.deleteEventById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Изменить мероприятие")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventRes.class))
                    })
    })
    @PutMapping("/event/{id}")
    public ResponseEntity<EventRes> updateEvent(
            @PathVariable(value = "id") @Min(value = 1, message = "Id не может быть меньше 1") Integer id,
            @RequestBody @Valid EventCreateReq req){

        Event res = eventService.updateEvent(id, req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EventRes.mapFromEntity(res));
    }

    @Operation(summary = "Добавить мероприятие в заявку")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationRes.class))
                    })
    })
    @PostMapping("/application/{id}/event/{eId}")
    @Secured("ROLE_USER")
    public ResponseEntity<ApplicationRes> addEventToApplication(
            @PathVariable(value = "id") Integer id,
            @PathVariable(value = "eId") @Min(value = 1, message = "Id не может быть меньше 1") Integer eId,
            Principal principal){
        ApplicationRes res = applicationService.addEvent(id, eId, principal.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Удалить мероприятие из заявки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationRes.class))
                    })
    })
    @Secured("ROLE_USER")
    @DeleteMapping("/application/{id}/event/{eId}")
    public ResponseEntity<ApplicationRes> deleteEventFromApplication(
            @PathVariable(value = "id") Integer id,
            @PathVariable(value = "eId") @Min(value = 1, message = "Id не может быть меньше 1") Integer eId,
            Principal principal){
        ApplicationRes res = applicationService.deleteEvent(id, eId, principal.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

}
