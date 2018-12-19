package com.example.ng_tiofack.mynews.controler.activities;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.controler.fragments.BusinessFragment;
import com.example.ng_tiofack.mynews.controler.fragments.MostPopularFragment;
import com.example.ng_tiofack.mynews.controler.fragments.TopStoriesFragment;
import com.example.ng_tiofack.mynews.model.SavedValues;
import com.example.ng_tiofack.mynews.model.Search;
import com.example.ng_tiofack.mynews.utils.SyncJob;
import com.example.ng_tiofack.mynews.utils.Utils;
import com.example.ng_tiofack.mynews.view.ViewPagerAdapter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle mToggle;
    private static int iD;
    SavedValues mySavedValues;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.configureToolbar();
        mySavedValues = Utils.getNotificationParam(this);

        TabLayout tabLayout = findViewById(R.id.tablayout_id);
        ViewPager viewPager = findViewById(R.id.viewpager_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Adding Fragments
        adapter.AddFragment(new TopStoriesFragment(), "TOP STORIES");
        adapter.AddFragment(new MostPopularFragment(), "MOST POPULAR");
        adapter.AddFragment(new BusinessFragment(), "BUSINESS");

        // adapter Setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.drawer_main_id);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (mySavedValues.getswitchParams()) {
            iD = schedulePeriodicJob();
        } else {
            cancelJob(iD);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }
    // ----

    private void configureToolbar() {
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
    }

    private int schedulePeriodicJob() {

        return new JobRequest.Builder(SyncJob.TAG)

                .setPeriodic(TimeUnit.DAYS.toMillis(1))
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .build()
                .schedule();
    }

    private void cancelJob(int jobId) {
        JobManager.instance().cancel(jobId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {

            case R.id.menu_activity_main_search:
                launchSearchActivity();
                return true;
            case R.id.menu_activity_main_notification:
                launchNotificationActivity();
                return true;
            case R.id.menu_activity_main_help:
                Toast.makeText(this, "Il n'y a rien à paramétrer ici, la fenetre help arrive...", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_activity_main_about:
                Toast.makeText(this, "Il n'y a rien à paramétrer ici, la fenetre about arrive...", Toast.LENGTH_LONG).show();
                return true;
            default:
                if (mToggle.onOptionsItemSelected(item)) {
                    return true;
                }
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        // 6 - Show fragment after user clicked on a menu item
        switch (id) {
            case R.id.menu_drawer_arts:
                Toast.makeText(this, "Il n'y a arts", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_drawer_healthfitness:
                Toast.makeText(this, "Il n'y a health and fitness", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_drawer_science:
                Toast.makeText(this, "Il n'y a science", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_drawer_money:
                Toast.makeText(this, "Il n'y a money", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // ----
    private void launchSearchActivity() {
        Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
        this.startActivityForResult(myIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {

                String resultString = data.getStringExtra(getString(R.string.results_from_search_activity));
                List<Search.Response.Doc> resultResponseDoc = Utils.getResultfromJson(resultString);
                int x = 9;
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "no result was found", Toast.LENGTH_SHORT).show();
                //Snackbar.make(View., "no result was found", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        }
    }

    private void launchNotificationActivity() {
        Intent myIntent = new Intent(MainActivity.this, NotificationsActivity.class);
        this.startActivity(myIntent);
    }

    // 3 - Generic method that will replace and show a fragment inside the MainActivity Frame Layout
    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            // getSupportFragmentManager().beginTransaction()                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }


}
