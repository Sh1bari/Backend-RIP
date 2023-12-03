package com.example.rip.controllers;

import com.example.rip.models.dtos.response.FileRes;
import com.example.rip.models.entities.File;
import com.example.rip.repos.FileRepo;
import com.example.rip.services.FileService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@RestController
@RequiredArgsConstructor
@Validated
public class FileController {

    private final FileService fileService;

    @PostMapping("/file/{id}")
    public ResponseEntity<FileRes> addFile(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "file", required = true) MultipartFile file){
        File res = fileService.saveFile(id, file);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(FileRes.mapFromEntity(res));
    }
}
