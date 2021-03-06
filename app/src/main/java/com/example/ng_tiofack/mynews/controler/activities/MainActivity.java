package com.example.ng_tiofack.mynews.controler.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.controler.fragments.MostPopularFragment;
import com.example.ng_tiofack.mynews.controler.fragments.NewsFragment;
import com.example.ng_tiofack.mynews.controler.fragments.TopStoriesFragment;
import com.example.ng_tiofack.mynews.model.SavedValues;
import com.example.ng_tiofack.mynews.utils.Utils;
import com.example.ng_tiofack.mynews.view.adapters.ViewPagerAdapter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final int REQUEST_CODE = 1;
    // Saved values class
    SavedValues mySavedValues;
    // define an ActionBarDrawerToggle
    private ActionBarDrawerToggle mToggle;
    //Drawer Layout
    private DrawerLayout drawerLayout;
    //ViewPager Adapter
    private ViewPagerAdapter adapter;
    //view Pager
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.configureToolbar();
        mySavedValues = Utils.getNotificationParam(this);

        TabLayout tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Adding Fragments
        adapter.addFragment(new TopStoriesFragment(), "TOP STORIES");
        adapter.addFragment(new MostPopularFragment(), "MOST POPULAR");
        adapter.addFragment(NewsFragment.newInstance(getString(R.string.news_desk_business)), "BUSINESS");

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

        // handle notifications results on the main activity
        this.handleNotificationResults(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    private void configureToolbar() {
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
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
                openHelp(Utils.UrlRouter);
                return true;
            case R.id.menu_activity_main_about:
                this.alertDialogAbout();
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
        String aArg = null;
        String tabName = "";

        // 6 - Show fragment after user clicked on a menu item
        switch (id) {
            case R.id.menu_drawer_arts:
                aArg = "news_desk:(\"Arts\")";
                tabName = getString(R.string.arts);
                break;
            case R.id.menu_drawer_healthfitness:
                aArg = "news_desk:(\"Health & Fitness\")";
                tabName = getString(R.string.health_and_fitness);
                break;
            case R.id.menu_drawer_science:
                aArg = "news_desk:(\"Science\")";
                tabName = getString(R.string.science);
                break;
            case R.id.menu_drawer_money:
                aArg = "news_desk:(\"Your Money\")";
                tabName = getString(R.string.your_money);
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        adapter.updateFragment(2, NewsFragment.newInstance(aArg), tabName);
        viewPager.setCurrentItem(2);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                String resultString = data.getStringExtra(getString(R.string.results_from_search_activity));
                adapter.updateFragment(2, NewsFragment.newInstance(resultString), "SEARCH");
                viewPager.setCurrentItem(2);
            }
            if (resultCode == RESULT_CANCELED) {
                Snackbar.make(getWindow().getDecorView().getRootView(), R.string.no_result_search, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        }
    }

    @Override //won't be called if no singleTop/singleTask attributes are used
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.handleNotificationResults(intent);
    }

    private void launchSearchActivity() {
        Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
        this.startActivityForResult(myIntent, 1);
    }

    private void launchNotificationActivity() {
        Intent myIntent = new Intent(MainActivity.this, NotificationsActivity.class);
        this.startActivity(myIntent);
    }

    private void handleNotificationResults(Intent intent) {

        //size from notification activity
        int numberOfArticlesFoundViaNotification = intent.getIntExtra(getString(R.string.results_from_notification_activity), -1);
        String notifyConfig = intent.getStringExtra(getString(R.string.config_param_notification_activity));

        if (numberOfArticlesFoundViaNotification != -1) {

            adapter.updateFragment(2, NewsFragment.newInstance(notifyConfig), "MY NOTIFICATION");
            viewPager.setCurrentItem(2);
        }
    }

    private void alertDialogAbout() {
        final ViewGroup parent = null;

        //inflate a custom view for the dialog
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(MainActivity.this);
        View mView = layoutInflaterAndroid.inflate(R.layout.about_alertdialog, parent, false);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(mView);

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });
        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    private void openHelp(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(), R.string.browser_required, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

    }

}
