package com.example.saksham.popular_movies;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.saksham.popular_movies.Data.MovieContract;

public class Fav extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
private FavMovieAdapter adapter;
private GridView gridview;
private static final int MOVIE_LOADER=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        gridview=(GridView) findViewById(R.id.favlist);
        View emptyView = findViewById(R.id.favempty);
        gridview.setEmptyView(emptyView);
        gridview.setAdapter(adapter);
        adapter=new FavMovieAdapter(this,null);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Uri uri1= ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENTURI,id);

                Intent intent=new Intent(Fav.this,DetailActivity.class);

                intent.setData(uri1);
                startActivity(intent);
            }
        });
        gridview.setAdapter(adapter);

        getLoaderManager().initLoader(MOVIE_LOADER,null,this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                MovieContract.MovieEntry.COLUMN_ID,
                MovieContract.MovieEntry.COLUMN_POSTER,
                MovieContract.MovieEntry.COLUMN_MOVIE_NAME,
                MovieContract.MovieEntry.COLUMN_Rating,
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
                MovieContract.MovieEntry.COLUMN_DESCRIPTION,
        };
        return new CursorLoader(this, MovieContract.MovieEntry.CONTENTURI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }


    public void deleteall()
    {

        long id= getContentResolver().delete(MovieContract.MovieEntry.CONTENTURI,null,null);
        if(id==0)
        {
            Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"DONE",Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.fav, menu);
        return true;

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_deleteall:
                deleteall();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
