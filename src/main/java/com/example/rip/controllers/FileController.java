package com.example.rip.controllers;

import com.example.rip.models.dtos.response.ApplicationRes;
import com.example.rip.models.dtos.response.FileRes;
import com.example.rip.models.entities.File;
import com.example.rip.repos.FileRepo;
import com.example.rip.services.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@Tag(name = "API файлов", description = "Контроллер для работы с файлами")
public class FileController {

    private final FileService fileService;

    @Operation(summary = "Добавить файл")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FileRes.class))
                    })
    })
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
