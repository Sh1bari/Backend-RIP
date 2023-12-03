package com.example.rip.exceptions.file;

import com.example.rip.exceptions.GlobalAppException;
import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class CantSaveFileException extends GlobalAppException {
    public CantSaveFileException() {
        super(500, "Cant save file");
    }
}
