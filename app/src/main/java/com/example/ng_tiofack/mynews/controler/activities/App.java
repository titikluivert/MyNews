package com.example.ng_tiofack.mynews.controler.activities;

import android.app.Application;
import android.os.StrictMode;

import com.evernote.android.job.JobManager;
import com.example.ng_tiofack.mynews.BuildConfig;
import com.example.ng_tiofack.mynews.utils.ReminderJobCreator;

/**
 * Created by NG-TIOFACK on 11/28/2018.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                    new StrictMode.ThreadPolicy.Builder()
                            .detectAll()
                            .penaltyLog()
                            .penaltyDeath()
                            .build());

            StrictMode.setVmPolicy(
                    new StrictMode.VmPolicy.Builder()
                            .detectAll()
                            .penaltyLog()
                            .penaltyDeath()
                            .build());
        }

        JobManager.create(this).addJobCreator(new ReminderJobCreator());
        //JobConfig.setAllowSmallerIntervalsForMarshmallow(true);
    }
}
