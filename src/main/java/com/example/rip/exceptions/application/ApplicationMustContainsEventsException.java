package com.example.rip.exceptions.application;

import com.example.rip.exceptions.GlobalAppException;
import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class ApplicationMustContainsEventsException extends GlobalAppException {
    public ApplicationMustContainsEventsException() {
        super(409, "В заявке должны быть мероприятия");
    }
}
