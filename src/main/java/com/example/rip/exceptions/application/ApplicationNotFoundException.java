package com.example.rip.exceptions.application;

import com.example.rip.exceptions.GlobalAppException;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class ApplicationNotFoundException extends GlobalAppException {
    public ApplicationNotFoundException(Integer id) {
        super(404, "Заявка с id " + id + " не найдена");
    }
}
