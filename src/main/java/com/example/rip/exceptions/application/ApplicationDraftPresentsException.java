package com.example.rip.exceptions.application;

import com.example.rip.exceptions.GlobalAppException;
import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class ApplicationDraftPresentsException extends GlobalAppException {
    public ApplicationDraftPresentsException() {
        super(409, "У пользователя есть заявка DRAFT, ошибка создания нового черовика");
    }
}
