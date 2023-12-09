package com.example.rip.controllers;

import com.example.rip.models.dtos.request.ApplicationVote;
import com.example.rip.models.dtos.response.ApplicationRes;
import com.example.rip.models.entities.Application;
import com.example.rip.models.enums.ApplicationStatus;
import com.example.rip.services.ApplicationService;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@RestController
@RequiredArgsConstructor
@Validated
public class ApplicationController {

    private final ApplicationService applicationService;
    private final String moderatorUsername = "admin";

    @GetMapping("/application/{id}")
    public ResponseEntity<ApplicationRes> getApplicationById(
            @PathVariable(value = "id") @Min(value = 1, message = "Id не может быть меньше 1") Integer id){
        ApplicationRes res = applicationService.getApplicationById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @GetMapping("/applications")
    public ResponseEntity<List<ApplicationRes>> getApplications(
            @RequestParam(value = "status", required = false, defaultValue = "FORMED")ApplicationStatus status,
            @RequestParam(value = "dateFrom", required = false, defaultValue = "2010-12-04T09:21:02.258000")LocalDateTime dateFrom){
        List<ApplicationRes> res = applicationService.getAllApplications(status, dateFrom);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @PutMapping("/application/{id}/form")
    public ResponseEntity<ApplicationRes> formApplication(
            @PathVariable(value = "id") @Min(value = 1, message = "Id не может быть меньше 1") Integer id){
        ApplicationRes res = applicationService.formApplication(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @PutMapping("/application/{id}/vote")
    public ResponseEntity<ApplicationRes> voteApplication(
            @PathVariable(value = "id") @Min(value = 1, message = "Id не может быть меньше 1") Integer id,
            @RequestBody ApplicationVote vote){
        ApplicationRes res = applicationService.voteApplication(id, vote, moderatorUsername);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @DeleteMapping("/application/{id}")
    public ResponseEntity<?> deleteApplication(
            @PathVariable(value = "id") @Min(value = 1, message = "Id не может быть меньше 1") Integer id){
        Application res = applicationService.deleteApplication(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
