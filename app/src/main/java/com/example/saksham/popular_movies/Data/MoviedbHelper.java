package com.example.saksham.popular_movies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Saksham on 02-02-2018.
 */

public class MoviedbHelper extends SQLiteOpenHelper {

    private static final String DatabaseName="movies.db";
    private static final int DatabaseVersion=2;

    public MoviedbHelper(Context context)
    {
        super(context,DatabaseName,null,DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createtable="CREATE TABLE "
                + MovieContract.MovieEntry.TABLE_NAME + " ("
                + MovieContract.MovieEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_NAME + " TEXT, "
                + MovieContract.MovieEntry.COLUMN_DESCRIPTION + " TEXT, "
                + MovieContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT, "
                + MovieContract.MovieEntry.COLUMN_Rating+ " TEXT, "
                + MovieContract.MovieEntry.COLUMN_POSTER+ " TEXT, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_PHOTO + " TEXT);";

        sqLiteDatabase.execSQL(createtable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
