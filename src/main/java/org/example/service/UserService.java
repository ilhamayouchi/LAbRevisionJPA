package org.example.service;

import org.example.dao.AbstractFacade;
import org.example.entities.User;

public class UserService extends AbstractFacade<User> {
    public UserService() {
        super(User.class);
    }
}
