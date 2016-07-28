package com.example.eatmeet.dao.interfaces;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.entities.User;

import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public interface UserDAO {

    void getUsers(final List<User> users, final BackendStatusManager backendStatusManager);

    void authenticate(User user, BackendStatusManager backendStatusManager);

    void unauthenticate(BackendStatusManager backendStatusManager);

    @Deprecated
    List<User> getUsers();
}
