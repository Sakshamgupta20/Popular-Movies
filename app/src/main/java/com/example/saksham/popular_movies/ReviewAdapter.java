package com.example.saksham.popular_movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Saksham on 06-02-2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder>  {

    private Context mContext;
    private List<Review> reviewList;
    int count=0;
    int flag=0;

    private final AdapterOnClickHandler1 mOnClickListener;

    public interface AdapterOnClickHandler1 {
        void onClick(Boolean click);
    }

    public ReviewAdapter(AdapterOnClickHandler1 clickHandler) {
        mOnClickListener=clickHandler;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {

        TextView name;
        TextView content;
        public MyViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.reviewauthor);
            content=view.findViewById(R.id.reviewcontent);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.name.setText(review.getAuthor());
        holder.content.setText(review.getReview());

    }

    @Override
    public int getItemCount() {
        if (null == reviewList) return 0;
        return reviewList.size();
    }

    public void setBakingData(List<Review> reviewData) {
        reviewList =reviewData;
        notifyDataSetChanged();
    }
}