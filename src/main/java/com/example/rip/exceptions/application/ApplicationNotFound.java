package com.example.rip.exceptions.application;

import com.example.rip.exceptions.GlobalAppException;
import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class ApplicationNotFound extends GlobalAppException {
    public ApplicationNotFound(Integer id) {
        super(404, "Заявка с id " + id + " не найдена");
    }
}
