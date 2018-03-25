package com.example.saksham.popular_movies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movies>>,SharedPreferences.OnSharedPreferenceChangeListener{


    private MoviesAdapter mAdapter;
    private static final String REQUEST_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = BuildConfig.API_KEY;
    private TextView mErrorMessageDisplay;
    private static final int MOVIE_LOADER_ID=1;
    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;
    private ProgressBar mLoadingIndicator;


    private String movieid;
   private GridView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
         list=(GridView) findViewById(R.id.list) ;



        mAdapter=new MoviesAdapter(this,new ArrayList<Movies>());
        list.setAdapter(mAdapter);



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Movies currentmovie=mAdapter.getItem(position);

                Intent detailintent=new Intent(MainActivity.this,DetailActivity.class);
                movieid=currentmovie.getMovieid();
                detailintent.putExtra("titlekey", currentmovie.getTitle());
                detailintent.putExtra("backposter",currentmovie.getDetailposter());
                detailintent.putExtra("plotkey",currentmovie.getPlot());
                detailintent.putExtra("ratingkey",currentmovie.getRaiting());
                detailintent.putExtra("datekey",currentmovie.getReleasedate());
                detailintent.putExtra("movieid",movieid);
                detailintent.putExtra("poster",currentmovie.getImageUrl());
                startActivity(detailintent);
            }
        });
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

loader();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (PREFERENCES_HAVE_BEEN_UPDATED) {
            mAdapter.clear();
            loaderrestart();
            PREFERENCES_HAVE_BEEN_UPDATED = false;
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

private void loader()
{
    showWeatherDataView();

    ConnectivityManager connMgr = (ConnectivityManager)
            getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

    if (networkInfo != null && networkInfo.isConnected()) {
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(MOVIE_LOADER_ID, null, this);

    }
    else
    {
        showErrorMessage();
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }
}
    private void loaderrestart()
    {
        showWeatherDataView();

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.restartLoader(MOVIE_LOADER_ID, null, this);
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
        else
        {
            showErrorMessage();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
    }


    private void showWeatherDataView() {

        mErrorMessageDisplay.setVisibility(View.INVISIBLE);

        list.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {

        list.setVisibility(View.INVISIBLE);

        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<List<Movies>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String order = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        String modified = REQUEST_URL+order+"?api_key="+API_KEY;
        return new MoviesLoader(this, modified);
    }

    @Override
    public void onLoadFinished(Loader<List<Movies>> loader, List<Movies> movies) {

        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mAdapter.clear();
        if (movies != null && movies.size()>0) {
            showWeatherDataView();
            mAdapter.addAll(movies);
        }
        else
        {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {
        mAdapter.clear();
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            mAdapter.clear();
            loaderrestart();
            return true;
        }
        if (id == R.id.action_settings) {
            Intent settingintent = new Intent(this, Settings.class);
            startActivity(settingintent);
            return true;
        }
        if (id == R.id.favourateintent) {

            Intent favintent = new Intent(this, Fav.class);
            startActivity(favintent);
            return true;
        }
            return super.onOptionsItemSelected(item);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        PREFERENCES_HAVE_BEEN_UPDATED=true;
    }

}