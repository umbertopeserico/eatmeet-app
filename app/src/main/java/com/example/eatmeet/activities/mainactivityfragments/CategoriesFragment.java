package com.example.eatmeet.activities.mainactivityfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.adapters.CategoriesAdapter;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.CategoryDAO;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.observablearraylist.ObservableArrayList;
import com.example.eatmeet.observablearraylist.OnAddListener;
import com.example.eatmeet.utils.Refreshable;
import com.example.eatmeet.utils.Visibility;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment implements Refreshable {

    private View view;
    private ListView categoriesListView;
    private CategoriesAdapter categoryAdapter;
    private ProgressBar loadingBar;
    private TextView messageLabel;
    private CategoryDAO categoryDAO;
    private ObservableArrayList<Category> categoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_categories, container, false);
        initViewElements();
        setActions();
        loadData();

        return view;
    }

    private void initViewElements() {
        loadingBar = (ProgressBar) view.findViewById(R.id.loadingBar);
        messageLabel = (TextView) view.findViewById(R.id.messagesLabel);
        categoryDAO = EatMeetApp.getDaoFactory().getCategoryDAO();
        categoryList = new ObservableArrayList<>();
        categoryAdapter = new CategoriesAdapter(getContext(), R.layout.list_item_category, categoryList);
        categoriesListView = (ListView) view.findViewById(R.id.categoriesListView);
        categoriesListView.setAdapter(categoryAdapter);
    }

    private void loadData() {
        categoryList.clear();
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                Visibility.makeInvisible(loadingBar);
                List<Category> categories = (List<Category>) response;
                if(categories.size() == 0) {
                    Visibility.makeVisible(messageLabel);
                    messageLabel.setText(getString(R.string.categories_no_category));
                }
                Logger.getLogger(CategoriesFragment.this.getClass().getName()).log(Level.INFO, "Connection succeded");
            }

            @Override
            public void onFailure(Object response, Integer code) {
                if(getActivity()!=null && isAdded()) {
                    Visibility.makeInvisible(loadingBar);
                    Visibility.makeVisible(messageLabel);
                    messageLabel.setText(getString(R.string.network_error));
                    Toast.makeText(CategoriesFragment.this.getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            }
        });

        categoryList.setOnAddListener(new OnAddListener() {
            @Override
            public void onAddEvent(Object item) {
                categoryAdapter.notifyDataSetChanged();
            }
        });

        Visibility.makeInvisible(messageLabel);
        Visibility.makeVisible(loadingBar);
        categoryDAO.getCategories(categoryList, backendStatusManager);
    }

    private void setActions() {

    }

    @Override
    public void refresh() {
        if(isAdded()) {
            loadData();
        }
    }
}