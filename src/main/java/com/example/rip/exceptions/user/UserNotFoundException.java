package com.example.rip.exceptions.user;

import com.example.rip.exceptions.GlobalAppException;
import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class UserNotFoundException extends GlobalAppException {
    public UserNotFoundException(Integer id) {
        super(404, "Пользователь с id " + id + " не найден");
    }
    public UserNotFoundException(String username) {
        super(404, "Пользователь с именем " + username + " не найден");
    }
}
