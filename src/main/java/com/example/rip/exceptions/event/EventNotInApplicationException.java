package com.example.rip.exceptions.event;

import com.example.rip.exceptions.GlobalAppException;
import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class EventNotInApplicationException extends GlobalAppException {
    public EventNotInApplicationException() {
        super(409, "Такого мероприятия нет в заявке");
    }
}
