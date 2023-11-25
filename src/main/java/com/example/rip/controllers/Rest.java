package com.example.rip.controllers;

import com.example.rip.repos.FileRepo;
import com.example.rip.services.EventService;
import lombok.*;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@RestController
@RequiredArgsConstructor
public class Rest {
    private final EventService eventService;
    private final FileRepo fileRepo;
    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable(name = "id")Integer id) {
        byte[] res = eventService.getFileByPath(fileRepo.findById(id).get().getPath());

        if(res == null){
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(res);
    }
}
