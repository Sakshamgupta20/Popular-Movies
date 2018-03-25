package com.example.saksham.popular_movies;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saksham on 27-01-2018.
 */

public class MoviesLoader extends AsyncTaskLoader<List<Movies>> {

    List<Movies> mMovieData = null;

    private String murl;

    MoviesLoader(Context context, String url)
    {
        super(context);
        murl=url;
    }

    @Override
    public List<Movies> loadInBackground() {
        if(murl==null)
        {
            return null;
        }
        List<Movies> result = NetworkUtils.fetchMoviesData(murl);
        return result;
    }

    @Override
    protected void onStartLoading() {
        if (mMovieData != null) {
            deliverResult(mMovieData);
        } else {
            forceLoad();
        }
    }

    public void deliverResult(List<Movies> data) {
        mMovieData = data;
        super.deliverResult(data);
    }
}
