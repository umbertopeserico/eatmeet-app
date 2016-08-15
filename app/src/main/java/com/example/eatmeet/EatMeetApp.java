package com.example.eatmeet;

import android.app.Application;
import android.util.Log;

import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.connections.HttpRestClient;
import com.example.eatmeet.dao.factories.DAOFactory;
import com.example.eatmeet.dao.factories.RestDAOFactory;
import com.example.eatmeet.dao.interfaces.UserDAO;
import com.example.eatmeet.entities.User;
import com.example.eatmeet.utils.FiltersManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by umberto on 25/06/16.
 */
public class EatMeetApp extends Application {
    private static FiltersManager filtersManager;
    private static final DAOFactory daoFactory = new RestDAOFactory();
    private static User currentUser;
    private static PropertyChangeSupport cs = new PropertyChangeSupport(EatMeetApp.class);

    @Override
    public void onCreate() {
        super.onCreate();
        filtersManager = new FiltersManager();
        HttpRestClient.setConfigurations(this);


        final UserDAO userDAO = EatMeetApp.getDaoFactory().getUserDAO();
        BackendStatusManager validateBSM = new BackendStatusManager();
        validateBSM.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                System.out.println(response);
                try {
                    JSONObject jsonResponse = new JSONObject((String) response);
                    Boolean status = Boolean.parseBoolean(jsonResponse.getString("success"));
                    if(status) {
                        JSONObject data = jsonResponse.getJSONObject("data");
                        Integer id = data.getInt("id");
                        User user = new User();
                        user.setId(id);
                        BackendStatusManager userBSM = new BackendStatusManager();
                        userBSM.setBackendStatusListener(new BackendStatusListener() {
                            @Override
                            public void onSuccess(Object response, Integer code) {
                                User user = (User) response;
                                Log.i("FOUND CURRENT USER:", "Trovato current user: "+user.toString());
                                EatMeetApp.setCurrentUser(user);
                            }

                            @Override
                            public void onFailure(Object response, Integer code) {
                                Log.e("CURRENT USER NOT FOUND:", response.toString());
                            }
                        });
                        userDAO.getUser(user, userBSM);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object response, Integer code) {

            }
        });

        userDAO.validateToken(validateBSM);
    }

    public static FiltersManager getFiltersManager() {
        return filtersManager;
    }

    public static DAOFactory getDaoFactory() {
        return daoFactory;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User oldValue = EatMeetApp.currentUser;
        EatMeetApp.currentUser = currentUser;
        EatMeetApp.cs.firePropertyChange("currentUser", oldValue, EatMeetApp.currentUser);
    }

    public static void addListener(PropertyChangeListener listener) {
        cs.addPropertyChangeListener(listener);
    }
}
