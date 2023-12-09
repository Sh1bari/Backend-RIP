package com.example.rip.exceptions.application;

import com.example.rip.exceptions.GlobalAppException;
import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class ApplicationNotValidVoteException extends GlobalAppException {
    public ApplicationNotValidVoteException() {
        super(400, "Ответ должен быть либо COMPLETED, либо REJECTED");
    }
}
