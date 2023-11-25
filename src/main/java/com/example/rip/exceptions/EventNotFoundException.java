package com.example.rip.exceptions;

import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class EventNotFoundException extends GlobalAppException{
    public EventNotFoundException(Integer id) {
        super(404, "Event with id " + id + " not found");
    }
}
