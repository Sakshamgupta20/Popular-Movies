package com.example.saksham.popular_movies;

import android.app.LoaderManager;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.saksham.popular_movies.Data.MovieContract;
import com.example.saksham.popular_movies.Data.MoviedbHelper;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private TextView mTitle;
    private ImageView mback;
    private TextView trai;

    private TextView mplot;
    private TextView mrating;
    private TextView mdate;

    private String sdate;
    private Uri current;
   private String title;
   private String backurl;
   private String plot;

    private String modified;
    private String rating;
    private String date="";
    private ToggleButton toggle;
    private  String movieid;
    private String poster;


    private String vurl;
    private String id;
    private static final String API_KEY = BuildConfig.API_KEY;
    private static final int MOVIE_LOADER = 3;
    private VideosAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Button reviewbutto=(Button)findViewById(R.id.reviewbutton);
        mTitle = (TextView) findViewById(R.id.title);
        mplot = (TextView) findViewById(R.id.plot);
        mrating = (TextView) findViewById(R.id.rating);
        mdate = (TextView) findViewById(R.id.date);
        trai=(TextView)findViewById(R.id.trailer);
         toggle=(ToggleButton)findViewById(R.id.toggle);
        mback = (ImageView) findViewById(R.id.backimage);
        ImageView imageView11=(ImageView)findViewById(R.id.image1);
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("movieid")) {
            movieid= intentThatStartedThisActivity.getStringExtra("movieid");

        }
        modified = "https://api.themoviedb.org/3/movie/" + movieid + "/videos" + "?api_key=" + API_KEY;

        LoaderManager loaderManager1 = getLoaderManager();
        loaderManager1.initLoader(MOVIE_LOADER, null, videodata);

        adapter=new VideosAdapter(this,new ArrayList<Videos>());

        ListView list=(ListView)findViewById(R.id.videolist);

        list.setAdapter(adapter);



        list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // I took this method from stackoverflow because trailer listview was not scrollable.
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Videos currentvideo=adapter.getItem(position);
                 id=currentvideo.getKey();
                 vurl="http://www.youtube.com/watch?v=" + id;
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + id));
                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
            }
        });

        Intent favintent = getIntent();
        current = favintent.getData();
        if(current==null)
        {
            detaildata();
            if (Exists())
            {
                toggle.setChecked(true);
            }

        }
        else
        {
            getLoaderManager().initLoader(0, null, this);
            toggle.setChecked(true);
            trai.setVisibility(View.GONE);
            reviewbutto.setVisibility(View.GONE);
            imageView11.setVisibility(View.GONE);

        }
    }
    public void detaildata()
    {
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {


            if (intentThatStartedThisActivity.hasExtra("poster")) {
                poster= intentThatStartedThisActivity.getStringExtra("poster");
                String modifiedback="http://image.tmdb.org/t/p/w500"+poster;

                if (poster.isEmpty()) {
                    mback.setImageResource(R.drawable.noimage);
                } else{
                    Picasso.with(this).load(modifiedback).into(mback);
                }
            }

            if (intentThatStartedThisActivity.hasExtra("titlekey")) {
                title = intentThatStartedThisActivity.getStringExtra("titlekey");
                mTitle.setText(title);
            }
            if (intentThatStartedThisActivity.hasExtra("backposter")) {
                backurl = intentThatStartedThisActivity.getStringExtra("backposter");

            }
            if (intentThatStartedThisActivity.hasExtra("plotkey")) {
                plot = intentThatStartedThisActivity.getStringExtra("plotkey");
                mplot.setText(plot);
            }
            if (intentThatStartedThisActivity.hasExtra("ratingkey")) {
                rating = intentThatStartedThisActivity.getStringExtra("ratingkey");
                String modifiedratind=rating+"/10";
                mrating.setText(modifiedratind);
            }
            if (intentThatStartedThisActivity.hasExtra("datekey")) {
                date = intentThatStartedThisActivity.getStringExtra("datekey");
                 sdate=date();
                mdate.setText(sdate);
            }
        }



        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle.isChecked()) {
                    insert();
                } else {
                    deleted();
                }
            }
        });

    }
public void Review(View view)
{
    Intent intent=new Intent(DetailActivity.this,MovieReview.class);
    intent.putExtra("movieid",movieid);
    startActivity(intent);
}
    public String date()
    {
        String year="";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {

            Date date1 = format.parse(date);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            year = df.format(date1);
        }catch (Exception e)
        {

        }
        return year;
    }

    public  boolean Exists() {
        ContentResolver cr = getContentResolver();
        String[] columns = { MovieContract.MovieEntry.COLUMN_MOVIE_ID };
        String selection = MovieContract.MovieEntry.COLUMN_MOVIE_ID + " =?";
        String[] selectionArgs = { movieid };

        Cursor cursor = cr.query(MovieContract.MovieEntry.CONTENTURI, columns,selection, selectionArgs, null,null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }


    private Uri newUri;
        public void insert()
        {
            ContentValues values = new ContentValues();
            values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movieid);
            values.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, title);
            values.put(MovieContract.MovieEntry.COLUMN_DESCRIPTION, plot);
            values.put(MovieContract.MovieEntry.COLUMN_Rating, rating);
            values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, sdate);
            values.put(MovieContract.MovieEntry.COLUMN_MOVIE_PHOTO, backurl);
            values.put(MovieContract.MovieEntry.COLUMN_POSTER,poster);
             newUri = getContentResolver().insert(MovieContract.MovieEntry.CONTENTURI, values);
            if (newUri == null) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Added To Favourites", Toast.LENGTH_SHORT).show();
            }
        }

        public void deleted()
        {
            Long id;
            ContentResolver cr = getContentResolver();
            String selection = MovieContract.MovieEntry.COLUMN_MOVIE_ID + " =?";
            String[] selectionArgs = { movieid };

            Cursor cursor = cr.query(MovieContract.MovieEntry.CONTENTURI, null,selection, selectionArgs, null,null);
            if (cursor.moveToFirst())
            {
                id = cursor.getLong(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID));
                Uri uri1= ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENTURI,id);
                long id1= getContentResolver().delete(uri1,null,null);
                if(id1==0)
                {
                    Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this,"Removed From Favourites",Toast.LENGTH_SHORT).show();
                }
            }
            cursor.close();
        }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                MovieContract.MovieEntry.COLUMN_ID,
                MovieContract.MovieEntry.COLUMN_MOVIE_ID,
                MovieContract.MovieEntry.COLUMN_MOVIE_NAME,
                MovieContract.MovieEntry.COLUMN_DESCRIPTION,
                MovieContract.MovieEntry.COLUMN_Rating,
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
                MovieContract.MovieEntry.COLUMN_POSTER,
        };
        return new CursorLoader(this,current,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if(cursor.moveToFirst())
        {
            int mnameColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_NAME);
            int descriptionColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_DESCRIPTION);
            int raitingColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_Rating);
            int dateColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE);
            int photoColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER);

            String moviename=cursor.getString(mnameColumnIndex);
            String description=cursor.getString(descriptionColumnIndex);
            String raiting=cursor.getString(raitingColumnIndex);
            String date=cursor.getString(dateColumnIndex);
            String photo=cursor.getString(photoColumnIndex);

            String temp=raiting+"/10";
            mTitle.setText(moviename);
            mdate.setText(date);
            mrating.setText(temp);
            mplot.setText(description);
            String modified="http://image.tmdb.org/t/p/w500"+photo;
            Picasso.with(this).load(modified).into(mback);

            toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (toggle.isChecked()) {
                        insert();
                    } else {
                        deletecurrent();
                    }
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mTitle.setText("");
        mdate.setText("");
        mrating.setText("");
        mplot.setText("");
        mback.setImageResource(0);
    }


    public void deletecurrent()
    {

        long id= getContentResolver().delete(current,null,null);
        if(id==0)
        {
            Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Removed From Favorites",Toast.LENGTH_SHORT).show();
        }
        finish();
    }



    private LoaderManager.LoaderCallbacks<List<Videos>> videodata =new LoaderManager.LoaderCallbacks<List<Videos>>() {
        @Override
        public Loader<List<Videos>> onCreateLoader(int i, Bundle bundle) {
            return  new VideosLoader(DetailActivity.this,modified);
        }

        @Override
        public void onLoadFinished(Loader<List<Videos>> loader, List<Videos> videos) {
            if (videos != null && videos.size() > 0) {
                adapter.addAll(videos);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<Videos>> loader) {
            adapter.clear();
        }
    };


    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (current!= null) {
            MenuItem menuItem = menu.findItem(R.id.action_share);
            menuItem.setVisible(false);
        }
        return true;
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            Videos currentvideo=adapter.getItem(0);
            String id1=currentvideo.getKey();
            vurl="http://www.youtube.com/watch?v=" + id1;
            sendIntent.putExtra(Intent.EXTRA_TEXT, vurl);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }

       if(id==android.R.id.home) {
           finish();
           return true;
       }
        return super.onOptionsItemSelected(item);
    }
}

