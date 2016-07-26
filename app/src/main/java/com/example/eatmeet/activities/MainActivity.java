package com.example.eatmeet.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.mainactivityfragments.CategoriesFragment;
import com.example.eatmeet.mainactivityfragments.EventsFragment;
import com.example.eatmeet.mainactivityfragments.GoogleMapFragment;
import com.example.eatmeet.utils.CookiesUtil;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;

    ArrayList<Integer> f_categories = new ArrayList<>();
    public boolean on_categories = false;

    Date f_min_date = null;
    Date f_max_date = null;
    public boolean on_min_date = false;
    public boolean on_max_date = false;

    double f_min_price = 0;
    double f_max_price = 0;
    public boolean on_min_price = false;
    public boolean on_max_price = false;

    double f_actual_discount = 0;
    public boolean on_actual_discount = false;

    int f_min_people = 0;
    int f_max_people = 0;
    public boolean on_min_people = false;
    public boolean on_max_people = false;

    ArrayList<Integer> f_restaurants = new ArrayList<>();
    public boolean on_restaurants = false;

    /*restaurants*/
    public void setF_restaurants(ArrayList<Integer> restaurants) {
        f_restaurants = restaurants;
        on_restaurants = true;
    }
    public void removeF_restaurants(){
        on_restaurants = false;
    }
    public ArrayList<Integer> getF_restaurants(){
        return f_restaurants;
    }

    /*categories*/
    public void setF_categories(ArrayList<Integer> categories) {
        f_categories = categories;
        on_categories = true;
    }
    public void removeF_categories(){
        on_categories = false;
    }
    public ArrayList<Integer> getF_categories(){
        return f_categories;
    }

    /*min_people*/
    public void setF_min_people(int min_people) {
        f_min_people = min_people;
        on_min_people = true;
    }
    public void removeF_min_people(){
        on_min_people = false;
    }
    public int getF_min_people(){
        return f_min_people;
    }

    /*max_people*/
    public void setF_max_people(int max_people) {
        f_max_people = max_people;
        on_max_people = true;
    }
    public void removeF_max_people(){
        on_max_people = false;
    }
    public int getF_max_people(){
        return f_max_people;
    }

    /*min_date*/
    public void f_min_date(Date min_date) {
        f_min_date = min_date;
        on_min_date = true;
    }
    public void removeF_min_date(){
        on_min_date = false;
    }
    public Date getF_min_date(){
        return f_min_date;
    }

    /*max_date*/
    public void setF_max_date(Date max_date) {
        f_max_date = max_date;
        on_max_date = true;
    }
    public void removeF_max_date(){
        on_max_date = false;
    }
    public Date getF_max_date(){
        return f_max_date;
    }

    /*min_price*/
    public void f_min_price(double min_price) {
        f_min_price = min_price;
        on_min_price = true;
    }
    public void removeF_min_price(){
        on_min_price = false;
    }
    public double getF_min_price(){
        return f_min_price;
    }

    /*max_price*/
    public void setF_max_price(double max_price) {
        f_max_price = max_price;
        on_max_price = true;
    }
    public void removeF_max_price(){
        on_max_price = false;
    }
    public double getF_max_price(){
        return f_max_price;
    }

    /*actual_discount*/
    public void setF_actual_discount(double actual_discount) {
        f_actual_discount = actual_discount;
        on_actual_discount = true;
    }
    public void removeF_actual_discount(){
        on_actual_discount = false;
    }
    public double getF_actual_discount(){
        return f_actual_discount;
    }

    /*allFilters*/
    public void removeAllFilters(){
        removeF_max_people();
        removeF_min_people();
        removeF_categories();
        removeF_restaurants();
        removeF_max_date();
        removeF_min_date();
        removeF_max_price();
        removeF_min_price();
        removeF_actual_discount();
    }

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
            long i = new SectionsPagerAdapter(getSupportFragmentManager()).getItemId(position);
            EventsFragment newFragment = (EventsFragment) getSupportFragmentManager().findFragmentByTag(
                    "android:switcher:" + mViewPager.getId() + ":" + i);
            newFragment.refresh();
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

        if(EatMeetApp.sharedPref.contains("email"))
        {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer_logged);
        } else
        {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        TextView emailText = (TextView) findViewById(R.id.emailTextSideBar);

        if(EatMeetApp.sharedPref.contains("email")) {
            assert emailText != null;
            emailText.setText(EatMeetApp.sharedPref.getString("email", "Ospite"));
        }
        else
        {
            emailText.setText("Ospite");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        System.out.println(id);
        Intent intent;

        switch(id) {
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
                SharedPreferences.Editor editor = EatMeetApp.sharedPref.edit();
                editor.clear();
                editor.commit();
                CookiesUtil.getCookieManager().getCookieStore().removeAll();
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.activity_main_drawer);
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
}
