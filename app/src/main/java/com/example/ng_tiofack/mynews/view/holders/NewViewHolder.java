package com.example.ng_tiofack.mynews.view.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.model.Search;
import com.example.ng_tiofack.mynews.utils.Utils;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NG-TIOFACK on 12/19/2018.
 */
public class NewViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_item_title)
    TextView Title;
    @BindView(R.id.fragment_item_section)
    TextView texSectionSubsection;
    @BindView(R.id.fragment_item_image)
    ImageView imageView;
    @BindView(R.id.fragment_item_date)
    TextView dateText;

    public NewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithNews(Search.Response.Doc news, RequestManager glide) {

        this.Title.setText(news.getSnippet());
        this.texSectionSubsection.setText(news.getDocumentType());
        this.dateText.setText(Utils.getConvertDate(news.getPubDate()));

        switch (news.getNewsDesk()) {

            case "Arts":
                glide.load(R.drawable.artlogo).apply(RequestOptions.circleCropTransform()).into(imageView);
                break;
            case "Health & Fitness":
                glide.load(R.drawable.healthfitnesslogo).apply(RequestOptions.circleCropTransform()).into(imageView);
                break;
            case "Science":
                glide.load(R.drawable.sciencelogo).apply(RequestOptions.circleCropTransform()).into(imageView);
                break;
            case "Your Money":
                glide.load(R.drawable.yourmoneylogo).apply(RequestOptions.circleCropTransform()).into(imageView);
                break;
            default:
                glide.load(R.drawable.file_search).apply(RequestOptions.circleCropTransform()).into(imageView);
                break;
        }
    }
}
