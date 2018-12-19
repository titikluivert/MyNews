package com.example.ng_tiofack.mynews.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import com.evernote.android.job.Job;
import com.example.ng_tiofack.mynews.R;

import com.example.ng_tiofack.mynews.model.ParamsOptions;
import com.example.ng_tiofack.mynews.model.SavedValues;
import com.example.ng_tiofack.mynews.model.SavedValuesParams;
import com.example.ng_tiofack.mynews.model.Search;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by NG-TIOFACK on 11/18/2018.
 */
public class SyncJob extends Job {

    public static final String TAG = "demo_tag";
    private DisposableObserver<Search> disposable;

    @Override
    @NonNull
    protected Result onRunJob(@NonNull Params params) {
        ParamsOptions paramsOptions = new ParamsOptions();
        List<String> articles = new ArrayList<>();
        SavedValues mySavedValues = Utils.getNotificationParam(this.getContext());
        SavedValuesParams savedValuesParams = paramsOptions.checkParamsOptions(this.getContext(), mySavedValues.getqueryItem(), null, null, mySavedValues.getCategories(), articles);
        disposable = SearchServiceStreams.streamFetchSearchItems(mySavedValues.getqueryItem(), savedValuesParams.getArticleschecked(), null, null, Utils.apiKeyNYT).subscribeWith(new DisposableObserver<Search>() {

            @Override
            public void onNext(Search results) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel(TAG, "Job Notification", NotificationManager.IMPORTANCE_HIGH);
                        channel.setDescription("Job notification");
                        Objects.requireNonNull(getContext().getSystemService(NotificationManager.class)).createNotificationChannel(channel);

                    }

                    Notification notification = new NotificationCompat.Builder(getContext(), TAG)
                            .setContentTitle("New notifications")
                            .setContentText(getContext().getString(R.string.notificationTitle, results.getResponse().getDocs().size()))
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                            .setColor(Color.GRAY)
                            .build();

                    NotificationManagerCompat.from(getContext()).notify(1, notification);


            }

            @Override
            public void onError(Throwable e) {
                Log.e("", "une erreur est survenue>" + e);
            }

            @Override
            public void onComplete() {
                Log.e("", "on complete is running");
            }
        });


        return Result.SUCCESS;
    }

}
