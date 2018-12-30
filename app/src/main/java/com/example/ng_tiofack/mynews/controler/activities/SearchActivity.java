package com.example.ng_tiofack.mynews.controler.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.model.ParamsOptions;
import com.example.ng_tiofack.mynews.model.SavedValuesParams;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class SearchActivity extends AppCompatActivity {


    private DatePickerDialog mDatePickerDialog;
    private Calendar calendar;
    private EditText dateView, dateView1, search_query_item;
    private int year, month, day;
    private ParamsOptions mParamsOptions = new ParamsOptions();
    private int categoriesChecked;
    private boolean[] categories;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.configureToolbar();

        dateView = findViewById(R.id.bigindateText);
        ImageButton imgBtn = findViewById(R.id.bigindatepicker);

        dateView1 = findViewById(R.id.enddateText);
        ImageButton imgBtn1 = findViewById(R.id.enddatepicker);
        this.datePickerMethod(imgBtn,dateView);
        this.datePickerMethod(imgBtn1,dateView1);

        Button searchBtn = findViewById(R.id.search_button);
        search_query_item = findViewById(R.id.searchqueryitem);
        final CheckBox[] checkbox_search = {findViewById(R.id.checkBoxArt), findViewById(R.id.checkBoxBusiness), findViewById(R.id.checkBoxEntrepreneurs),
                findViewById(R.id.checkBoxpolitics), findViewById(R.id.checkBoxsports), findViewById(R.id.checkBoxtravel)};

        categoriesChecked = 0;

        if (categories == null) categories = new boolean[checkbox_search.length];
        this.configCategories(checkbox_search);
        this.searchButtonMethod(searchBtn);
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

    private void searchButtonMethod(Button searchButton) {

        if (searchButton != null) {
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    List<String> articles = new ArrayList<>();
                    SavedValuesParams savedValuesParams = mParamsOptions.checkParamsOptions(SearchActivity.this, search_query_item.getText().toString(), dateView.getText().toString(), dateView1.getText().toString(), categories, articles);

                    if (savedValuesParams.getQueryItem().equals("SYMPTHOME_I")) {
                        Sneaker.with(SearchActivity.this)
                                .setTitle("Error")
                                .setMessage("you must enter a query item")
                                .sneakError();

                    } else if (savedValuesParams.getQueryItem().equals("SYMPTHOME_II")) {
                        Sneaker.with(SearchActivity.this)
                                .setTitle("Error")
                                .setMessage("the bigin date shall not be greater than the end date")
                                .sneakError();
                    } else if (savedValuesParams.getQueryItem().equals("SYMPTHOME_III")) {
                        Sneaker.with(SearchActivity.this)
                                .setTitle("Error")
                                .setMessage("please you must check a least one box")
                                .sneakError();
                    } else {
                        String result = savedValuesParams.getQueryItem() + ";" +
                                savedValuesParams.getArticleschecked() + ";" +
                                savedValuesParams.getDatebegin() + ";" +
                                savedValuesParams.getDateend();
                        finishResult(result);
                    }
                }
            });

        }
    }

    private void finishResult(String param_config) {

        Intent resultIntent = new Intent();
        resultIntent.putExtra(getString(R.string.results_from_search_activity), param_config);
        setResult(RESULT_OK, resultIntent);
        finish();

    }

    private void configCategories(CheckBox[] checkBoxes) {

        if (checkBoxes != null) {

            for (int i = 0; i < checkBoxes.length; i++) {
                final int finalI = i;

                checkBoxes[finalI]
                        .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                categoriesChecked += isChecked ? 1 : -1;
                                categories[finalI] = isChecked;

                            }
                        });
            }
        }
    }

    private void datePickerMethod(ImageButton imageButton, final EditText date_view) {

        if (imageButton != null) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);

                    mDatePickerDialog = new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int myYear, int myMonth, int dayOfMonth) {
                            date_view.setText(dayOfMonth + "/" + (myMonth + 1) + "/" + myYear);
                        }
                    }, year, month, day);
                    mDatePickerDialog.show();
                }

            });

        }

    }

}