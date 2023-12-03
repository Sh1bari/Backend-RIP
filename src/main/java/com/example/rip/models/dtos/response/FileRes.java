package com.example.rip.models.dtos.response;

import com.example.rip.models.entities.File;
import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileRes {

    private Integer id;

    private String name;

    private String type;

    private Long size;

    private String path;

    public static FileRes mapFromEntity(File file) {
        return FileRes.builder()
                .id(file.getId())
                .name(file.getName())
                .type(file.getType())
                .size(file.getSize())
                .path(file.getPath())
                .build();
    }
}
