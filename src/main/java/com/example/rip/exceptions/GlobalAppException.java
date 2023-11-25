package com.example.rip.exceptions;

import lombok.*;

import java.util.Date;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
public abstract class GlobalAppException extends RuntimeException {
    protected int status;
    protected String message;
    protected Date timestamp;

    public GlobalAppException(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
