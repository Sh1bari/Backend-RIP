package com.example.rip.exceptions.event;

import com.example.rip.exceptions.GlobalAppException;
import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class EventAlreadyInApplicationException extends GlobalAppException {
    public EventAlreadyInApplicationException() {
        super(409, "Мероприятие уже добавлено в заявку");
    }
}
