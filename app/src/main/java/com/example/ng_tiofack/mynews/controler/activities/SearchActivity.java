package com.example.ng_tiofack.mynews.controler.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.model.ParamsOptions;
import com.example.ng_tiofack.mynews.model.SavedValuesParams;
import com.example.ng_tiofack.mynews.model.Search;
import com.example.ng_tiofack.mynews.utils.streams.SearchServiceStreams;
import com.example.ng_tiofack.mynews.utils.Utils;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import io.reactivex.observers.DisposableObserver;


public class SearchActivity extends AppCompatActivity  {

    /*
    Solution 1:
    Modifier SearchActivity pour qu'il prenne 2 fragments:
    - Un fragment pour afficher les éléments de la recherche
    - Un fragment pour afficher les résultats
    On bascule d'un fragment à un autre grâce à getSupportFragmentManager (et les transactions via replace)

    Soltuion 2:
    MainActivity lance SearchActivity avec startActivityForResult
    Quand SearchActivity à récupéré les résultats de la recherche elle renvoie les articles trouvés à MainActivity
    MainActivity se charge de faire l'affichage
     */

    private DatePickerDialog mDatePickerDialog;
    private Calendar calendar;
    private EditText dateView, dateView1, search_query_item;
    private int year, month, day;
    private ParamsOptions mParamsOptions = new ParamsOptions();
    int categoriesChecked;
    boolean[] categories;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.configureToolbar();

        dateView = findViewById(R.id.bigindateText);
        ImageButton imgBtn = findViewById(R.id.bigindatepicker);

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                mDatePickerDialog = new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myYear, int myMonth, int dayOfMonth) {
                        dateView.setText(dayOfMonth + "/" + (myMonth + 1) + "/" + myYear);
                    }
                }, year, month, day);
                mDatePickerDialog.show();
            }
        });

        dateView1 = findViewById(R.id.enddateText);
        ImageButton imgBtn1 = findViewById(R.id.enddatepicker);

        imgBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                mDatePickerDialog = new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myYear, int myMonth, int dayOfMonth) {
                        dateView1.setText(dayOfMonth + "/" + (myMonth + 1) + "/" + myYear);
                    }
                }, year, month, day);
                mDatePickerDialog.show();
            }
        });

        Button searchBtn = findViewById(R.id.search_button);
        search_query_item = findViewById(R.id.searchqueryitem);
        final CheckBox[] chbx_search = {findViewById(R.id.checkBoxArt), findViewById(R.id.checkBoxBusiness), findViewById(R.id.checkBoxEntrepreneurs),
                findViewById(R.id.checkBoxpolitics), findViewById(R.id.checkBoxsports), findViewById(R.id.checkBoxtravel)};

        categoriesChecked = 0;

        if (categories == null) categories = new boolean[chbx_search.length];
        for (int i = 0; i < chbx_search.length; i++) {
            final int finalI = i;

            chbx_search[finalI]
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            categoriesChecked += isChecked ? 1 : -1;
                            categories[finalI] = isChecked;

                        }
                    });
        }

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> articles = new ArrayList<>();
                SavedValuesParams savedValuesParams = mParamsOptions.checkParamsOptions(SearchActivity.this, search_query_item.getText().toString(), dateView.getText().toString(), dateView1.getText().toString(), categories, articles);

                if (savedValuesParams.getqueryItem().equals("SYMPTHOME_I")) {
                    Sneaker.with(SearchActivity.this)
                            .setTitle("Error")
                            .setMessage("you must enter a query item")
                            .sneakError();

                } else if (savedValuesParams.getqueryItem().equals("SYMPTHOME_II"))
                {
                    Sneaker.with(SearchActivity.this)
                            .setTitle("Error")
                            .setMessage("the bigin date shall not be greater than the end date")
                            .sneakError();
                }

                else if (savedValuesParams.getqueryItem().equals("SYMPTHOME_III"))
                {
                    Sneaker.with(SearchActivity.this)
                            .setTitle("Error")
                            .setMessage("please you must check a least one box")
                            .sneakError();
                }
                else{
                    executeHttpRequestWithRetrofit(SearchActivity.this,
                            savedValuesParams.getQueryItem(),
                            savedValuesParams.getArticleschecked(),
                            savedValuesParams.getDatebegin(),
                            savedValuesParams.getDateend(),
                            Utils.apiKeyNYT);
                }
            }
        });
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

    private void executeHttpRequestWithRetrofit(final Context ctx, String query_item, String articles_checked, String begin_date, String end_date, String key) {
        DisposableObserver<Search> disposable = SearchServiceStreams.streamFetchSearchItems(query_item, articles_checked, begin_date, end_date, key).subscribeWith(new DisposableObserver<Search>() {

            @Override
            public void onNext(Search results) {
                if (results.getResponse().getDocs().isEmpty()) {
                    //Toast.makeText(ctx, "no result was found", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                    finish();
                } else {
                    Toast.makeText(ctx, results.getResponse().getDocs().size() + " articles", Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(getString(R.string.results_from_search_activity), Utils.setResulttoJson(results.getResponse().getDocs()));
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
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
    }

}