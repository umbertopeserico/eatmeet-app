package com.example.eatmeet.dao.implementations.oldrest;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.UserDAO;
import com.example.eatmeet.entities.User;

import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public class UserDAOImpl implements UserDAO {
    @Override
    public void getUsers(List<User> users, BackendStatusManager backendStatusManager) {

    }

    @Override
    public void signIn(User user, BackendStatusManager backendStatusManager) {

    }

    @Override
    public void signUp(User user, BackendStatusManager backendStatusManager) {

    }

    @Override
    public void signOut(BackendStatusManager backendStatusManager) {

    }

    @Override
    public List<User> getUsers() {
        return null;
    }
}
