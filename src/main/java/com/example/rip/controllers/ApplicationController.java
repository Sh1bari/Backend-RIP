package com.example.rip.controllers;

import com.example.rip.exceptions.AppError;
import com.example.rip.models.dtos.request.ApplicationVote;
import com.example.rip.models.dtos.response.ApplicationAllRes;
import com.example.rip.models.dtos.response.ApplicationRes;
import com.example.rip.models.dtos.response.JwtResponse;
import com.example.rip.models.entities.Application;
import com.example.rip.models.enums.ApplicationStatus;
import com.example.rip.repos.UserRepo;
import com.example.rip.services.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

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
@Tag(name = "API заявок", description = "Контроллер для работы с заявками")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final UserRepo userRepo;

    @Operation(summary = "Получить заявку по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationRes.class))
                    })
    })
    @Secured("ROLE_USER")
    @GetMapping("/application/{id}")
    public ResponseEntity<ApplicationRes> getApplicationById(
            @PathVariable(value = "id") @Min(value = 1, message = "Id не может быть меньше 1") Integer id,
            Principal principal){
        ApplicationRes res = applicationService.getApplicationById(id, principal.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Получить все заявки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationAllRes.class))
                    })
    })
    @Secured("ROLE_USER")
    @GetMapping("/applications")
    public ResponseEntity<List<ApplicationAllRes>> getApplications(
            @RequestParam(value = "dateFrom", required = false, defaultValue = "2010-12-04T09:21:02.258000")LocalDateTime dateFrom,
            Principal principal){
        List<ApplicationAllRes> res;
        if(userRepo.findByUsername(principal.getName()).get().getRoles().stream().anyMatch(o->o.getName().equals("ROLE_ADMIN"))){
            res = applicationService.getAllApplications(dateFrom);
        }else {
            res = applicationService.getAllApplicationsByUser(principal.getName(), dateFrom);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Сформировать заявку")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationRes.class))
                    })
    })
    @Secured("ROLE_USER")
    @PutMapping("/application/{id}/form")
    public ResponseEntity<ApplicationRes> formApplication(
            @PathVariable(value = "id") @Min(value = 1, message = "Id не может быть меньше 1") Integer id,
            Principal principal){
        ApplicationRes res = applicationService.formApplication(principal.getName(), id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Проголосовать за заявку")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationRes.class))
                    })
    })
    @PutMapping("/application/{id}/vote")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApplicationRes> voteApplication(
            @PathVariable(value = "id") @Min(value = 1, message = "Id не может быть меньше 1") Integer id,
            @RequestBody ApplicationVote vote,
            Principal principal){
        ApplicationRes res = applicationService.voteApplication(id, vote, principal.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Удалить заявку")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "")
    })
    @Secured("ROLE_USER")
    @DeleteMapping("/application/{id}")
    public ResponseEntity<?> deleteApplication(
            @PathVariable(value = "id") @Min(value = 1, message = "Id не может быть меньше 1") Integer id,
            Principal principal){
        Application res = applicationService.deleteApplication(principal.getName(), id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
