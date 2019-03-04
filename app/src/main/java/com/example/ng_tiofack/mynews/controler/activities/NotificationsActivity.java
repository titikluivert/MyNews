package com.example.ng_tiofack.mynews.controler.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.model.SavedValues;
import com.example.ng_tiofack.mynews.utils.SyncJob;
import com.example.ng_tiofack.mynews.utils.Utils;

import java.util.concurrent.TimeUnit;

public class NotificationsActivity extends BaseSearchNotifyActivity {

    //ID for notification job
    private static int iD;
    // variable for different categories
    boolean[] categories;
    //Saved values class
    SavedValues mySavedValues;
    //flag to check if the categories are checked or not
    private int categoriesChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mySavedValues = Utils.getNotificationParam(this);
        queryItem.setText(mySavedValues.getqueryItem());

        categories = mySavedValues.getCategories();
        if (categories == null) categories = new boolean[checkboxSearch.length];

        mSwitch.setChecked(mySavedValues.getswitchParams());
        this.queryItemWatch();

        categoriesChecked = 0;

        this.configCategories(checkboxSearch);

        for (int i = 0; i < categories.length; i++) {
            checkboxSearch[i].setChecked(categories[i]);
        }
        this.switchConfigParams(mSwitch);

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
    protected void onPause() {
        super.onPause();
        Utils.saveNotificationParam(NotificationsActivity.this, mSwitch.isChecked(), queryItem.getText().toString(), categories);
    }

    private void switchConfigParams(final Switch mSwitch) {
        if (mSwitch != null) {


            mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (!queryItem.getText().toString().isEmpty()) {

                        if ((categoriesChecked > 0) && (mSwitch.isChecked())) {
                            mSwitch.setChecked(true);
                            iD = schedulePeriodicJob();
                        } else {
                            mSwitch.setChecked(false);
                            cancelJob(iD);
                            if (!(categoriesChecked > 0))
                                Snackbar.make(getWindow().getDecorView().getRootView(), R.string.empty_checkbox_msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    } else {
                        mSwitch.setChecked(false);
                        cancelJob(iD);
                        Snackbar.make(getWindow().getDecorView().getRootView(), R.string.missing_query_item_msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }

                }

            });
        }
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

                                categoriesChecked += isChecked ? 1 : -1;
                                categories[finalI] = isChecked;

                                if (categoriesChecked == 0) {
                                    mSwitch.setChecked(false);

                                }
                            }
                        });
            }
        }
    }

    @Override
    protected void hideElements() {
        // hide date und search button for notification view.
        findViewById(R.id.biginDateRelLayout).setVisibility(View.GONE);
        findViewById(R.id.endDateRelLayout).setVisibility(View.GONE);
        findViewById(R.id.search_button).setVisibility(View.GONE);

    }

    private void queryItemWatch() {

        queryItem.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (queryItem.getText().toString().isEmpty()) {
                    mSwitch.setChecked(false);
                }

            }

            public void afterTextChanged(Editable s) {

            }

        });

    }
}