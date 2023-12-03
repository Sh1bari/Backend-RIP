package com.example.rip.exceptions.event;

import com.example.rip.exceptions.GlobalAppException;
import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class EventNotFoundException extends GlobalAppException {
    public EventNotFoundException(Integer id) {
        super(404, "Мероприятие с id " + id + " не найдено");
    }
}
