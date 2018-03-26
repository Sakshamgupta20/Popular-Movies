package com.example.saksham.popular_movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Saksham on 26-01-2018.
 */

public class MoviesAdapter extends ArrayAdapter<Movies> {
    private Context context;
    public MoviesAdapter(Context context, List<Movies>movie) {
        super(context, 0, movie);
        this.context=context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.main_list_view, parent, false);
        }


        Movies currentmovie = getItem(position);


        ImageView poster1=(ImageView)listItemView.findViewById(R.id.poster);

        String poster=currentmovie.getImageUrl();

        String a="http://image.tmdb.org/t/p/w780"+poster;

         if (poster.isEmpty()) {
           poster1.setImageResource(R.drawable.noimage);
         } else{
        Picasso.get().load(a).into(poster1);
         }

        return listItemView;
    }
}
