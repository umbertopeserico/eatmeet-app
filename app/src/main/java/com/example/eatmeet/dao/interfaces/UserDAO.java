package com.example.eatmeet.dao.interfaces;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.entities.User;

import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public interface UserDAO {

    void getUsers(final List<User> users, final BackendStatusManager backendStatusManager);

    void signIn(User user, BackendStatusManager backendStatusManager);

    void signOut(BackendStatusManager backendStatusManager);

    void signUp(User user, final BackendStatusManager backendStatusManager);

    void getUser(User user, BackendStatusManager backendStatusManager);

    void validateToken(BackendStatusManager backendStatusManager);

    void getPastEvents(final List<Event> events, BackendStatusManager backendStatusManager);

    void getFutureEvents(final List<Event> events, BackendStatusManager backendStatusManager);

    @Deprecated
    List<User> getUsers();
}
