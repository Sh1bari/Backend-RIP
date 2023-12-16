package com.example.rip.exceptions.application;

import com.example.rip.exceptions.GlobalAppException;
import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class PermissionDeniedException extends GlobalAppException {
    public PermissionDeniedException() {
        super(403, "У вас нет прав");
    }
}
