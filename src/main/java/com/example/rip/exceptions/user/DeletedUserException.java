package com.example.rip.exceptions.user;


import com.example.rip.exceptions.GlobalAppException;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class DeletedUserException extends GlobalAppException {
    public DeletedUserException(String username) {
        super(403, "User " + username + " has been banned");
    }
}
