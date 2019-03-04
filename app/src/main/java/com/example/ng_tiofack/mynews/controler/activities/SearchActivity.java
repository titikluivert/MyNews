package com.example.ng_tiofack.mynews.controler.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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


public class SearchActivity extends BaseSearchNotifyActivity {


    private DatePickerDialog mDatePickerDialog;
    private Calendar calendar;
    private int year, month, day;
    private ParamsOptions mParamsOptions;
    private boolean[] categories;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mParamsOptions = new ParamsOptions(this.getResources().getStringArray(R.array.topicArray));

        this.datePickerMethod(imgBtn, dateBegin);
        this.datePickerMethod(imgBtn1, dateEnd);

        if (categories == null) categories = new boolean[checkboxSearch.length];
        this.configCategories(checkboxSearch);
        this.searchButtonMethod(searchBtn);
    }

    private void searchButtonMethod(Button searchButton) {

        if (searchButton != null) {
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SavedValuesParams savedValuesParams = mParamsOptions.checkParamsOptions(queryItem.getText().toString(), dateBegin.getText().toString(), dateEnd.getText().toString(), categories);

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

    @Override
    protected void configCategories(CheckBox[] checkBoxes) {

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

    @Override
    protected void hideElements() {

        // hide date und search button for search view.
        findViewById(R.id.enable_notifications).setVisibility(View.GONE);
        findViewById(R.id.viewNotification).setVisibility(View.GONE);
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