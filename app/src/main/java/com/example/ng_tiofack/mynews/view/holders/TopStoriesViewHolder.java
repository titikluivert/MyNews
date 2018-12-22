package com.example.ng_tiofack.mynews.view.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.model.TopStories;
import com.example.ng_tiofack.mynews.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NG-TIOFACK on 9/27/2018.
 */
public class TopStoriesViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.fragment_top_story_item_tittle)
    TextView Title;
    @BindView(R.id.fragment_top_story_item_section_subsection)
    TextView texSectionSubsection;
    @BindView(R.id.fragment_top_story_item_image)
    ImageView imageView;
    @BindView(R.id.fragment_top_story_item_date)
    TextView dateText;


    public TopStoriesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithTopStories(TopStories.Result topStories, RequestManager glide) {

        this.Title.setText(topStories.getTitle());
        String section_subsection = topStories.getSection() + " > " + topStories.getSubsection();
        this.texSectionSubsection.setText(section_subsection);
        this.dateText.setText(Utils.getConvertDate(topStories.getCreatedDate()));

        if (topStories.getMultimedia().size() == 0) {

            glide.load(R.drawable.newyork_time_img).apply(RequestOptions.circleCropTransform()).into(imageView);
        } else {

            glide.load(topStories.getMultimedia().get(0).getUrl()).apply(RequestOptions.circleCropTransform()).into(imageView);
        }

    }
}