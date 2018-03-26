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

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Saksham on 06-02-2018.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.MyViewHolder>  {

    private Context mContext;
    private List<Videos> videoList;


    private final AdapterOnClickHandler mOnClickListener;

    public interface AdapterOnClickHandler {
        void onClick(String videourl);
    }

    public VideosAdapter(Context mContext,AdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        mOnClickListener=clickHandler;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

         TextView name;
        ImageView image;
        public MyViewHolder(View view) {
            super(view);

            name=view.findViewById(R.id.videotitle);
            image=view.findViewById(R.id.nameid);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String name=videoList.get(adapterPosition).getKey();
            mOnClickListener.onClick(name);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Videos videos = videoList.get(position);
        holder.name.setText(videos.getName());
        String a=videos.getKey();
        if(TextUtils.isEmpty(a))
        {
            Picasso.get().load(R.drawable.ic_play_arrow_black_24dp).into(holder.image);
        }
        else
        {
            String modified="http://img.youtube.com/vi/"+a+"/0.jpg";
            Picasso.get().load(modified).into(holder.image);
        }

    }

    @Override
    public int getItemCount() {
        if (null == videoList) return 0;
        return videoList.size();
    }

    public void setBakingData(List<Videos> videoData) {
        videoList = videoData;
        notifyDataSetChanged();
    }
}

