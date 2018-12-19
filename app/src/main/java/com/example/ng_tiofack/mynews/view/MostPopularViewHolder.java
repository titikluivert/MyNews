package com.example.ng_tiofack.mynews.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.model.MostPopular;
import com.example.ng_tiofack.mynews.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NG-TIOFACK on 10/3/2018.
 */
public class MostPopularViewHolder  extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_most_popular_item_tittle)
    TextView Title;
    @BindView(R.id.fragment_most_popular_item_section)
    TextView texSectionSubsection;
    @BindView(R.id.fragment_most_popular_item_image)
    ImageView imageView;
    @BindView(R.id.fragment_most_popular_item_date)
    TextView dateText;


    public MostPopularViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithMostPopular(MostPopular.Result mostPopular, RequestManager glide) {

        this.Title.setText(mostPopular.getTitle());
        this.texSectionSubsection.setText(mostPopular.getSection());
        this.dateText.setText(Utils.getConvertDate(mostPopular.getPublishedDate()));

        if (mostPopular.getMedia().get(0).getMediaMetadata().size() == 0) {

            glide.load(R.drawable.newyork_time_img).apply(RequestOptions.circleCropTransform()).into(imageView);
        } else {

            glide.load(mostPopular.getMedia().get(0).getMediaMetadata().get(1).getUrl()).apply(RequestOptions.circleCropTransform()).into(imageView);
        }


    }
}
