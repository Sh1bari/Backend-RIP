package com.example.rip.services;

import com.example.rip.exceptions.file.CantSaveFileException;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioClient minioClient;

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

    public String putFile(String bucket, String path, MultipartFile file){
        deleteFile(bucket, path);
        return saveFile(bucket, file);
    }

    public void deleteFile(String bucketName, String objectPath) {
        try {
            // Подготовка аргументов для удаления
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectPath)
                    .build();

            // Выполнение операции удаления
            minioClient.removeObject(removeObjectArgs);

            System.out.println("Файл успешно удален.");
        } catch (MinioException e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String saveFile(String bucket, MultipartFile file) {
        try {
            // Генерируем уникальное имя файла
            String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());

            // Открываем поток для загрузки файла в MinIO
            InputStream fileStream = file.getInputStream();

            // Сохраняем файл в MinIO
            try {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucket)
                                .object(uniqueFileName)
                                .stream(fileStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            }catch (Exception e){
                throw new CantSaveFileException();
            }

            // Закрываем поток
            fileStream.close();

            // Возвращаем уникальное имя файла
            return uniqueFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateUniqueFileName(String name) {
        String currentDate = getCurrentDateTime();
        // Генерируем UUID и добавляем оригинальное расширение файла
        return currentDate + name;
    }

    private static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy-HH-mm");
        return dateFormat.format(new Date());
    }
}
