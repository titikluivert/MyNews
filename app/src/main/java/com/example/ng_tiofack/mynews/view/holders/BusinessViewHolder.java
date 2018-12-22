package com.example.ng_tiofack.mynews.view.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.model.Business;
import com.example.ng_tiofack.mynews.utils.Utils;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NG-TIOFACK on 10/5/2018.
 */
public class BusinessViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_item_title)
    TextView Title;
    @BindView(R.id.fragment_item_section)
    TextView texSectionSubsection;
    @BindView(R.id.fragment_item_image)
    ImageView imageView;
    @BindView(R.id.fragment_item_date)
    TextView dateText;


    public BusinessViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithBusiness(Business.Response.Doc business, RequestManager glide) {

        this.Title.setText(business.getSnippet());
        this.texSectionSubsection.setText(business.getDocumentType());
        this.dateText.setText(Utils.getConvertDate(business.getPubDate()));

        glide.load(R.drawable.newyork_time_img).apply(RequestOptions.circleCropTransform()).into(this.imageView);


    }
}
