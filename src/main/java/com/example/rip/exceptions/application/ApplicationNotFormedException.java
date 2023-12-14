package com.example.rip.exceptions.application;

import com.example.rip.exceptions.GlobalAppException;
import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class ApplicationNotFormedException extends GlobalAppException {
    public ApplicationNotFormedException() {
        super(409, "Заявка должна быть в состоянии FORMED или DRAFT");
    }
}
