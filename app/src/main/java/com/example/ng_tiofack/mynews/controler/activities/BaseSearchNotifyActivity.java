package com.example.ng_tiofack.mynews.controler.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.model.ParamsOptions;

import java.util.Objects;

public abstract class BaseSearchNotifyActivity extends AppCompatActivity {


    protected EditText dateBegin, dateEnd, queryItem;
    protected ParamsOptions mParamsOptions;
    //switch button to enable or disable notifications
    protected Switch mSwitch;
    protected CheckBox[] checkboxSearch;
    protected ImageButton imgBtn, imgBtn1;
    protected Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_notif_seach);
        this.configureToolbar();
        this.hideElements();

        dateBegin = findViewById(R.id.bigindateText);
        imgBtn = findViewById(R.id.bigindatepicker);

        mSwitch = findViewById(R.id.enable_notifications);

        mParamsOptions = new ParamsOptions(this.getResources().getStringArray(R.array.topicArray));

        dateEnd = findViewById(R.id.enddateText);
        imgBtn1 = findViewById(R.id.enddatepicker);

        searchBtn = findViewById(R.id.search_button);
        queryItem = findViewById(R.id.queryitem);
        checkboxSearch = new CheckBox[]{findViewById(R.id.checkBoxArt), findViewById(R.id.checkBoxBusiness), findViewById(R.id.checkBoxEntrepreneurs),
                findViewById(R.id.checkBoxpolitics), findViewById(R.id.checkBoxsports), findViewById(R.id.checkBoxtravel)};


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

    protected abstract void configCategories(CheckBox[] checkBoxes);

    protected abstract void hideElements();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
