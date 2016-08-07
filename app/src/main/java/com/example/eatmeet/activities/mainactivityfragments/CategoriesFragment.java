package com.example.eatmeet.activities.mainactivityfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.eatmeet.utils.Notificable;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment implements Refreshable {

    private ListView listView;
    private CategoriesAdapter categoryAdapter;
    private ProgressBar loadingBar;
    private TextView messageLabel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        loadingBar = (ProgressBar) view.findViewById(R.id.loadingBar);
        messageLabel = (TextView) view.findViewById(R.id.messagesLabel);

        CategoryDAO categoryDAO = EatMeetApp.getDaoFactory().getCategoryDAO();
        ObservableArrayList<Category> categoryList = new ObservableArrayList<>();
        BackendStatusManager backendStatusManager = new BackendStatusManager();

        categoryAdapter = new CategoriesAdapter(getContext(), R.layout.list_item_category, categoryList);

        listView = (ListView) view.findViewById(R.id.listview_categories);
        listView.setAdapter(categoryAdapter);
        backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                loadingBar.setVisibility(View.GONE);
                List<Category> categories = (List<Category>) response;
                if(categories.size() == 0) {
                    messageLabel.setVisibility(View.VISIBLE);
                    messageLabel.setText(getString(R.string.categories_no_category));
                }
                Logger.getLogger(CategoriesFragment.this.getClass().getName()).log(Level.INFO, "Connection succeded");
            }

            @Override
            public void onFailure(Object response, Integer code) {
                if(getActivity()!=null && isAdded()) {
                    loadingBar.setVisibility(View.GONE);
                    messageLabel.setVisibility(View.VISIBLE);
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

        messageLabel.setVisibility(View.GONE);
        loadingBar.setVisibility(View.VISIBLE);
        categoryDAO.getCategories(categoryList, backendStatusManager);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void refresh() {

    }
}