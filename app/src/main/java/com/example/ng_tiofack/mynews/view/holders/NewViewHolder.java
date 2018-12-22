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
        if (news.getMultimedia().size() == 0) {

            glide.load(R.drawable.newyork_time_img).apply(RequestOptions.circleCropTransform()).into(imageView);
        } else {

            glide.load(news.getMultimedia().get(0).getUrl()).apply(RequestOptions.circleCropTransform()).into(imageView);
        }

    }
}
