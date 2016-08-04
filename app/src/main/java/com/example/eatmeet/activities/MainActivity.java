package com.example.eatmeet.activities;

import android.content.Intent;
import android.os.Build;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;

    private void chooseMenuType() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Use navigationView
            System.out.println("version >= lollipop");
        } else {
            //use NavigationView
            System.out.println("version < lollipop");
        }
    }
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
            //System.out.println(getSupportFragmentManager().findFragmentByTag("unique_tag"));
            //((EventsFragment) getSupportFragmentManager().findFragmentById(fragment)).refresh();
            //((EventsFragment) getSupportFragmentManager().getFragments().get(fragment)).refresh();
            /*
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.replace(R.id.fragment_events, new EventsFragment());
            trans.commit();
            */
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chooseMenuType();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

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
        mDrawerLayout.setDrawerListener(toggle);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        setMenuLayout();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        System.out.println(id);
        Intent intent;

        switch(id) {
            case R.id.sidebar_my_events:
                intent = new Intent(MainActivity.this, MyEventsActivity.class);
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
                UserDAO userDAO = EatMeetApp.getDaoFactory().getUserDAO();
                BackendStatusManager backendStatusManager = new BackendStatusManager();
                backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
                    @Override
                    public void onSuccess(Object response, Integer code) {
                        EatMeetApp.setCurrentUser(null);
                        Toast.makeText(MainActivity.this, "Log out effettuato correttamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Object response, Integer code) {
                        Toast.makeText(MainActivity.this, "Errore nel logout. Si prega di riprovare", Toast.LENGTH_SHORT).show();
                    }
                });

                userDAO.signOut(backendStatusManager);
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.activity_main_drawer);
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = null;
            switch (position) {
                case 0:
                    f =  new CategoriesFragment();
                    break;
                case 1:
                    f =  new EventsFragment();
                    break;
                case 2:
                    f =  new GoogleMapFragment();
                    break;
                default:
                    f =  new CategoriesFragment();
            }

            return f;
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
