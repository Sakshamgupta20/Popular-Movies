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
 * Created by Saksham on 26-01-2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>  {

    private Context mContext;
    private List<Movies> movieList;


    private final AdapterOnClickHandler mOnClickListener;

    public interface AdapterOnClickHandler {
        void onClick(String titlekey,String backposter,String plotkey,String ratingkey,String datekey,String movieid,String poster);
    }



    public MoviesAdapter(Context mContext,AdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        mOnClickListener=clickHandler;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView image;
        public MyViewHolder(View view) {
            super(view);

            image=view.findViewById(R.id.poster);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String titlekey=movieList.get(adapterPosition).getTitle();
            String  backposter=movieList.get(adapterPosition).getDetailposter();
            String  plotkey=movieList.get(adapterPosition).getPlot();
            String  ratingkey=movieList.get(adapterPosition).getRaiting();
            String  datekey=movieList.get(adapterPosition).getReleasedate();
            String  movieid=movieList.get(adapterPosition).getMovieid();
            String  poster=movieList.get(adapterPosition).getImageUrl();

            mOnClickListener.onClick(titlekey,backposter,plotkey,ratingkey,datekey,movieid,poster);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_list_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movies movies = movieList.get(position);
        String poster=movies.getImageUrl();
        String a="http://image.tmdb.org/t/p/w780"+poster;
        if (poster.isEmpty()) {
            holder.image.setImageResource(R.drawable.noimage);
        } else{
            Picasso.get().load(a).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        if (null == movieList) return 0;
        return movieList.size();
    }

    public void setBakingData(List<Movies> movieData) {
        movieList = movieData;
        notifyDataSetChanged();
    }
    public void clear() {
        movieList=null;
        notifyDataSetChanged();
    }
}




