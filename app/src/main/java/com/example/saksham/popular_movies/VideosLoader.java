package com.example.saksham.popular_movies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Saksham on 06-02-2018.
 */

public class VideosLoader extends AsyncTaskLoader<List<Videos>> {

    List<Videos> mMovieData = null;

    private String murl;

    VideosLoader(Context context, String url)
    {
        super(context);
        murl=url;
    }

    @Override
    public List<Videos> loadInBackground() {
        if(murl==null)
        {
            return null;
        }
        List<Videos> result = NetworkUtils.fetchVideosData(murl);
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

    public void deliverResult(List<Videos> data) {
        mMovieData = data;
        super.deliverResult(data);
    }
}
