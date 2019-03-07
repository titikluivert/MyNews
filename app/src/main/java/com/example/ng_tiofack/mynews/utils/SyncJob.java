package com.example.ng_tiofack.mynews.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.evernote.android.job.Job;
import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.controler.activities.MainActivity;
import com.example.ng_tiofack.mynews.model.ArticlesNews;
import com.example.ng_tiofack.mynews.model.ParamsOptions;
import com.example.ng_tiofack.mynews.model.SavedValues;
import com.example.ng_tiofack.mynews.model.SavedValuesParams;
import com.example.ng_tiofack.mynews.utils.streams.SearchServiceStreams;

import java.util.Objects;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by NG-TIOFACK on 11/18/2018.
 */
public class SyncJob extends Job {

    public static final String TAG = "notificationTag";

    @Override
    @NonNull
    protected Result onRunJob(@NonNull Params params) {
        ParamsOptions paramsOptions = new ParamsOptions(getContext().getResources().getStringArray(R.array.topicArray));

        SavedValues mySavedValues = Utils.getNotificationParam(this.getContext());
        final SavedValuesParams savedValuesParams = paramsOptions.checkParamsOptions(mySavedValues.getqueryItem(), null, null, mySavedValues.getCategories());
        DisposableObserver<ArticlesNews> disposable = SearchServiceStreams.streamFetchSearchItems(mySavedValues.getqueryItem(), savedValuesParams.getArticleschecked(), null, null, Utils.apiKeyNYT).subscribeWith(new DisposableObserver<ArticlesNews>() {


            @Override
            public void onNext(ArticlesNews results) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(TAG, "Job Notification", NotificationManager.IMPORTANCE_HIGH);
                    channel.setDescription("Job notification");
                    Objects.requireNonNull(getContext().getSystemService(NotificationManager.class)).createNotificationChannel(channel);

                }

                Intent resultIntent = new Intent(getContext(), MainActivity.class);
                resultIntent.putExtra(getContext().getString(R.string.results_from_notification_activity), results.getResponse().getDocs().size());
                resultIntent.putExtra(getContext().getString(R.string.config_param_notification_activity), mySavedValuesToString(savedValuesParams));
                PendingIntent resultPendingIntent = PendingIntent.getActivity(getContext(), 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification notification = new NotificationCompat.Builder(getContext(), TAG)
                        .setContentTitle("New notifications")
                        .setContentText(getContext().getString(R.string.notificationTitle, results.getResponse().getDocs().size()))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                        .setColor(Color.GRAY)
                        .setAutoCancel(true)
                        .setContentIntent(resultPendingIntent)
                        .build();

                NotificationManagerCompat.from(getContext()).notify(1, notification);


            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                Log.e("", "on complete is running");
            }
        });


        return Result.SUCCESS;
    }


    private String mySavedValuesToString(SavedValuesParams valuesParams) {
        return valuesParams.getQueryItem() + ";" + valuesParams.getArticleschecked() + ";" +
                "null" + ";" +
                "null";
    }
}
