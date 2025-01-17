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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.activities.mainactivityfragments.EventsFragment;
import com.example.eatmeet.utils.Refreshable;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.UserDAO;
import com.example.eatmeet.activities.mainactivityfragments.CategoriesFragment;
import com.example.eatmeet.activities.mainactivityfragments.GoogleMapFragment;
import com.example.eatmeet.entities.User;
import com.example.eatmeet.utils.Visibility;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RelativeLayout overlay;
    private ProgressBar loadingBar;
    NavigationView navigationView;

    private int fragmentPrevious = 0;
    private int fragmentActual;

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

    public void trackFragments(int newFragment){
        fragmentPrevious = fragmentActual;
        fragmentActual = newFragment;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking() && !event.isCanceled()) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putExtra("destination", "" + fragmentPrevious);
            startActivity(intent);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void setCurrentFragment(int position){
        mViewPager.setCurrentItem(position, true);
        /*Refreshable refreshable = (Refreshable) mSectionsPagerAdapter.getCurrentFragment();
        if(refreshable!=null)
            refreshable.refresh();*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Codice per fixare il freeze della mappa sul primo cambio tab
        final MapView mapView = new MapView(this);
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

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Refreshable refreshable = (Refreshable) mSectionsPagerAdapter.getCurrentFragment();
                if(refreshable!=null)
                    refreshable.refresh();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
        Visibility.makeVisible(overlay);
        Visibility.makeVisible(loadingBar);
        UserDAO userDAO = EatMeetApp.getDaoFactory().getUserDAO();
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                Visibility.makeInvisible(overlay);
                Visibility.makeInvisible(loadingBar);
                EatMeetApp.setCurrentUser(null);
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.activity_main_drawer);
                Toast.makeText(MainActivity.this, R.string.sign_out_success, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("destination", "1");
                startActivity(intent);
            }

            @Override
            public void onFailure(Object response, Integer code) {
                Visibility.makeInvisible(overlay);
                Visibility.makeInvisible(loadingBar);
                Toast.makeText(MainActivity.this, R.string.sign_out_error, Toast.LENGTH_LONG).show();
            }
        });

        userDAO.signOut(backendStatusManager);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private Fragment mCurrentFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new CategoriesFragment();
                case 1:
                    return new EventsFragment();
                case 2:
                    return new GoogleMapFragment();
                default:
                    return new CategoriesFragment();
            }
        }

        @Override
        public int getCount() {
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

        public Fragment getCurrentFragment() {
            return mCurrentFragment;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((Fragment) object);
            }
            super.setPrimaryItem(container, position, object);
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
            Visibility.makeVisible(emailText);
        } else {
            fullNameTextSideBar.setText("Ospite");
            emailText.setText("");
            Visibility.makeInvisible(emailText);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null) {
            String applyFilters;
            applyFilters = (String) intent.getExtras().get("applyFilters");
            if (applyFilters!= null) {
                if (applyFilters.equals("1")) {
                    EatMeetApp.getFiltersManager().setEnabledFilters(true);
                }
            }

            String applyOrder;
            applyOrder = (String) intent.getExtras().get("applyOrder");
            if (applyOrder!= null) {
                if (applyOrder.equals("1")) {
                    EatMeetApp.getFiltersManager().setEnabledOrder(true);
                }
            }

            String destination;
            destination = (String) intent.getExtras().get("destination");
            System.out.println("DESTINATION: "+destination);
            if (destination!= null) {
                setCurrentFragment(Integer.parseInt(destination));
            }
        }
        setMenuLayout();
    }
}
