package com.example.rip.exceptions.file;

import com.example.rip.exceptions.GlobalAppException;
import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class FileNotFoundException extends GlobalAppException {
    public FileNotFoundException(Integer id) {
        super(404, "Файл с id " + id + " не найден");
    }
}
