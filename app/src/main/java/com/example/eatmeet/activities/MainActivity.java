package com.example.eatmeet.activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.UserDAO;
import com.example.eatmeet.activities.mainactivityfragments.CategoriesFragment;
import com.example.eatmeet.activities.mainactivityfragments.EventsFragment;
import com.example.eatmeet.activities.mainactivityfragments.GoogleMapFragment;
import com.example.eatmeet.entities.User;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RelativeLayout overlay;
    private ProgressBar loadingBar;
    NavigationView navigationView;

    /*private void chooseMenuType() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Use navigationView
            System.out.println("version >= lollipop");
        } else {
            //use NavigationView
            System.out.println("version < lollipop");
        }
    }*/
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;

    public void setCurrentFragment(int position){
        mViewPager.setCurrentItem(position, true);
        if(position==1) {
            long i = mSectionsPagerAdapter.getItemId(position);
            EventsFragment newFragment = (EventsFragment) getSupportFragmentManager().findFragmentByTag(
                    "android:switcher:" + mViewPager.getId() + ":" + i);
            if(newFragment != null) {
                newFragment.refresh();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Codice per fixare il freeze della mappa sul primo cambio tab
        MapView mapView = new MapView(this);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.i("LOADED MAP", "OK");
            }
        });
        // Fine codice per fixare il freeze della mappa sul primo cambio tab

        //chooseMenuType();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        overlay = (RelativeLayout) findViewById(R.id.overlay);
        loadingBar = (ProgressBar) findViewById(R.id.loadingBar);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout    );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                drawerView.bringToFront();
                drawerView.requestLayout();
                TextView emailText = (TextView) findViewById(R.id.emailTextSideBar);
            }

        };
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setMenuLayout();

        EatMeetApp.addListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                if(propertyChangeEvent.getPropertyName().equals("currentUser")) {
                    setMenuLayout();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        setMenuLayout();

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        System.out.println(id);
        Intent intent;

        switch(id) {
            case R.id.sidebar_my_events:
                intent = new Intent(MainActivity.this, UserEventsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.sidebar_signup:
                intent = new Intent(MainActivity.this, SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.sidebar_signin:
                intent = new Intent(MainActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.sidebar_signout:
                doLogout();
                break;
            case R.id.sidebar_privacy_policy:
                intent = new Intent(MainActivity.this, PrivacyPolicyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.sidebar_account_settings:
                intent = new Intent(MainActivity.this, AccountSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            default:
        }

        item.setChecked(false);
        mDrawerLayout.closeDrawers();

        return true;
    }

    private void doLogout() {
        overlay.setVisibility(View.VISIBLE);
        loadingBar.setVisibility(View.VISIBLE);
        UserDAO userDAO = EatMeetApp.getDaoFactory().getUserDAO();
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                overlay.setVisibility(View.GONE);
                loadingBar.setVisibility(View.GONE);
                EatMeetApp.setCurrentUser(null);
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.activity_main_drawer);
                Toast.makeText(MainActivity.this, R.string.sign_out_success, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onFailure(Object response, Integer code) {
                overlay.setVisibility(View.GONE);
                loadingBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, R.string.sign_out_error, Toast.LENGTH_SHORT).show();
            }
        });

        userDAO.signOut(backendStatusManager);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new CategoriesFragment());
            fragments.add(new EventsFragment());
            fragments.add(new GoogleMapFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.category_tab_title);
                case 1:
                    return getString(R.string.events_tab_title);
                case 2:
                    return getString(R.string.map_tab_title);
            }
            return null;
        }
    }

    private void setMenuLayout() {
        if(EatMeetApp.getCurrentUser()!=null)
        {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer_logged);
        } else
        {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }

        View view = navigationView.getHeaderView(0);
        TextView fullNameTextSideBar = (TextView) view.findViewById(R.id.fullNameTextSideBar);
        TextView emailText = (TextView) view.findViewById(R.id.emailTextSideBar);

        User currentUser = EatMeetApp.getCurrentUser();
        if(currentUser!=null) {
            fullNameTextSideBar.setText(currentUser.getFullName());
            emailText.setText(currentUser.getEmail());
            emailText.setVisibility(View.VISIBLE);
        } else {
            fullNameTextSideBar.setText("Ospite");
            emailText.setText("");
            emailText.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null && intent.getExtras().get("from") != null) {
            String from = (String) intent.getExtras().get("from");
            if (from.equals("FiltersActivity")) {
                setCurrentFragment(1);
            }
        }
        setMenuLayout();
    }
}
