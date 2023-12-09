package com.example.rip.services;

import com.example.rip.exceptions.file.FileNotFoundException;
import com.example.rip.models.entities.File;
import com.example.rip.repos.FileRepo;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class FileService {

    private final MinioService minioService;
    private final FileRepo fileRepo;

    public File saveFile(Integer id, MultipartFile multipartFile){

        File file = fileRepo.findById(id)
                        .orElseThrow(()->new FileNotFoundException(id));
        file.setName(multipartFile.getOriginalFilename());
        file.setType(multipartFile.getContentType());
        file.setSize(multipartFile.getSize());
        if(file.getPath() != null){
            file.setPath(minioService. putFile("rip", file.getPath(), multipartFile));
        }else {
            file.setPath(minioService.saveFile("rip", multipartFile));
        }
        fileRepo.save(file);

        return file;
    }
}
