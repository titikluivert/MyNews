package com.example.ng_tiofack.mynews.controler.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.model.SavedValues;
import com.example.ng_tiofack.mynews.utils.Utils;

import java.util.Objects;

public class NotificationsActivity extends AppCompatActivity {

    // variable for different categories
    boolean[] categories;
    //query item
    EditText search_query_item;
    //switch button to enable or disable notifications
    Switch mSwitch;
    //flag to check if the categories are checked or not
    private int categoriesChecked;
    //Saved values class
    SavedValues mySavedValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        this.configureToolbar();

        mSwitch = findViewById(R.id.enable_notifications);
        search_query_item = findViewById(R.id.editText);
        final CheckBox[] chbx_search = {findViewById(R.id.checkBox1), findViewById(R.id.checkBox2), findViewById(R.id.checkBox3),
                findViewById(R.id.checkBox4), findViewById(R.id.checkBox5), findViewById(R.id.checkBox6)};

        mySavedValues = Utils.getNotificationParam(this);
        search_query_item.setText(mySavedValues.getqueryItem());

        categories = mySavedValues.getCategories();
        if (categories == null) categories = new boolean[chbx_search.length];

        mSwitch.setChecked(mySavedValues.getswitchParams());
        search_query_item.addTextChangedListener(new TextWatcher() {

                                                      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                     }

                                                     public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                         if (search_query_item.getText().toString().isEmpty()) {
                                                             mSwitch.setChecked(false);
                                                         }

                                                     }

                                                     public void afterTextChanged(Editable s) {

                                                     }
                                                 }
        );

        categoriesChecked = 0;

        for (int i = 0; i < chbx_search.length; i++) {
            final int finalI = i;

            chbx_search[finalI]
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            categoriesChecked += isChecked ? 1 : -1;
                            categories[finalI] = isChecked;

                            if (categoriesChecked == 0) {
                                mSwitch.setChecked(false);

                            }
                        }
                    });
        }

        for (int i = 0; i < categories.length; i++) {
            chbx_search[i].setChecked(categories[i]);
        }

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!search_query_item.getText().toString().isEmpty()) {

                    if ((categoriesChecked > 0) && (mSwitch.isChecked())){
                        mSwitch.setChecked(true);

                    } else {
                        mSwitch.setChecked(false);
                        if(!(categoriesChecked > 0))
                        Snackbar.make(getWindow().getDecorView().getRootView(), R.string.empty_checkbox_msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                } else {
                    mSwitch.setChecked(false);
                    Snackbar.make(getWindow().getDecorView().getRootView(), R.string.missing_query_item_msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }

            }

        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.saveNotificationParam(NotificationsActivity.this, mSwitch.isChecked(), search_query_item.getText().toString(), categories);
    }

    private void configureToolbar() {
        //Get the toolbar (Serialise)
        Toolbar toolbar = findViewById(R.id.toolbar);
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }

}