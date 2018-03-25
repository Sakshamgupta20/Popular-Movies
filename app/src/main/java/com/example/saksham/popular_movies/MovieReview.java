package com.example.saksham.popular_movies;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MovieReview extends AppCompatActivity {

    private static final int MOVIE_LOADER_IDr = 2;
    private static final String API_KEY = BuildConfig.API_KEY;
    private  ReviewAdapter adapter;
    private String movieid;
    private String modified;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_review);

        Intent intent=getIntent();
        movieid = intent.getStringExtra("movieid");

         modified = "https://api.themoviedb.org/3/movie/" + movieid + "/reviews" + "?api_key=" + API_KEY;

         LoaderManager loaderManager1 = getLoaderManager();
        loaderManager1.initLoader(MOVIE_LOADER_IDr, null, reviewdata);

        ListView listView=(ListView)findViewById(R.id.reviewlist);

        adapter=new ReviewAdapter(this,new ArrayList<Review>());

        listView.setAdapter(adapter);



    }


    private LoaderManager.LoaderCallbacks<List<Review>> reviewdata = new LoaderManager.LoaderCallbacks<List<Review>>() {
        @Override
        public Loader<List<Review>> onCreateLoader(int i, Bundle bundle) {

            return new ReviewLoader(MovieReview.this, modified);
        }

        @Override
        public void onLoadFinished(Loader<List<Review>> loader, List<Review> reviews) {
            if (reviews != null && reviews.size() > 0) {
                adapter.addAll(reviews);
            }
            else
            {
                TextView view=(TextView)findViewById(R.id.emptyreview);
                view.setVisibility(View.VISIBLE);
            }
        }


        @Override
        public void onLoaderReset(Loader<List<Review>> loader) {
            adapter.clear();
        }
    };


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                    finish();
                    return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

