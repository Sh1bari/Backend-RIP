package com.example.rip.services;

import com.example.rip.repos.FileRepo;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.MinioException;
import lombok.*;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioClient minioClient;
    private final FileRepo fileRepo;

    public InputStream getFile(String objectName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket("rip")
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
