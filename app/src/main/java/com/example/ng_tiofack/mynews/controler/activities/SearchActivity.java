package com.example.ng_tiofack.mynews.controler.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

import java.util.Calendar;
import java.util.Objects;


public class SearchActivity extends AppCompatActivity {


    private DatePickerDialog mDatePickerDialog;
    private Calendar calendar;
    private EditText dateBegin, dateEnd, search_query_item;
    private int year, month, day;
    private ParamsOptions mParamsOptions;
    private boolean[] categories;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.configureToolbar();

        dateBegin = findViewById(R.id.bigindateText);
        ImageButton imgBtn = findViewById(R.id.bigindatepicker);

        mParamsOptions = new ParamsOptions(this.getResources().getStringArray(R.array.topicArray));

        dateEnd = findViewById(R.id.enddateText);
        ImageButton imgBtn1 = findViewById(R.id.enddatepicker);
        this.datePickerMethod(imgBtn, dateBegin);
        this.datePickerMethod(imgBtn1, dateEnd);

        Button searchBtn = findViewById(R.id.search_button);
        search_query_item = findViewById(R.id.searchqueryitem);
        final CheckBox[] checkbox_search = {findViewById(R.id.checkBoxArt), findViewById(R.id.checkBoxBusiness), findViewById(R.id.checkBoxEntrepreneurs),
                findViewById(R.id.checkBoxpolitics), findViewById(R.id.checkBoxsports), findViewById(R.id.checkBoxtravel)};

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void searchButtonMethod(Button searchButton) {

        if (searchButton != null) {
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SavedValuesParams savedValuesParams = mParamsOptions.checkParamsOptions(search_query_item.getText().toString(), dateBegin.getText().toString(), dateEnd.getText().toString(), categories);

                    switch (savedValuesParams.getQueryItem()) {
                        case "SYMPTHOME_I":
                            Sneaker.with(SearchActivity.this)
                                    .setTitle("Error")
                                    .setMessage("you must enter a query item")
                                    .sneakError();

                            break;
                        case "SYMPTHOME_II":
                            Sneaker.with(SearchActivity.this)
                                    .setTitle("Error")
                                    .setMessage("the bigin date shall not be greater than the end date")
                                    .sneakError();
                            break;
                        case "SYMPTHOME_III":
                            Sneaker.with(SearchActivity.this)
                                    .setTitle("Error")
                                    .setMessage("please you must check a least one box")
                                    .sneakError();
                            break;
                        default:
                            String result = savedValuesParams.getQueryItem() + ";" +
                                    savedValuesParams.getArticleschecked() + ";" +
                                    savedValuesParams.getDatebegin() + ";" +
                                    savedValuesParams.getDateend();
                            finishResult(result);
                            break;
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