package com.example.saksham.popular_movies;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.saksham.popular_movies.Data.MovieContract;
import com.squareup.picasso.Picasso;

/**
 * Created by Saksham on 02-02-2018.
 */

public class FavMovieAdapter extends CursorAdapter {

    FavMovieAdapter(Context context, Cursor c)
    {
        super(context,c,0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.fav_list,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String poster=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER));

        ImageView imageView=(ImageView) view.findViewById(R.id.favposter);

        String a="http://image.tmdb.org/t/p/w780"+poster;

        if (poster.isEmpty()) {
            imageView.setImageResource(R.drawable.noimage);
        } else{
            Picasso.with(context).load(a).into(imageView);
        }

    }
}
