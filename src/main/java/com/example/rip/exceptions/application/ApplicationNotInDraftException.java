package com.example.rip.exceptions.application;

import com.example.rip.exceptions.GlobalAppException;
import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class ApplicationNotInDraftException extends GlobalAppException {

    public ApplicationNotInDraftException() {
        super(409, "Заявка должна быть в состоянии DRAFT");
    }
}
