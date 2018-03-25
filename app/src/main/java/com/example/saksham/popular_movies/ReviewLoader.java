package com.example.saksham.popular_movies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Saksham on 05-02-2018.
 */

public class ReviewLoader extends AsyncTaskLoader<List<Review>> {

    List<Review> mMovieData = null;

    private String murl;

    ReviewLoader(Context context, String url)
    {
        super(context);
        murl=url;
    }

    @Override
    public List<Review> loadInBackground() {
        if(murl==null)
        {
            return null;
        }
        List<Review> result = NetworkUtils.fetchReviewData(murl);
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

    public void deliverResult(List<Review> data) {
        mMovieData = data;
        super.deliverResult(data);
    }
}
