package com.example.rip.exceptions;

import lombok.*;

import java.util.Date;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
public class AppError {
    private int status;
    private String message;
    private Date timestamp;

    public AppError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
