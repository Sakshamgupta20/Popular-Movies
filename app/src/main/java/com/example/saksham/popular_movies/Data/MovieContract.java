package com.example.saksham.popular_movies.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Saksham on 02-02-2018.
 */

public class MovieContract {

    public static final String CONTENT_AUTHORITY="com.example.saksham.popular_movies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES="movies";

    public static abstract class  MovieEntry implements BaseColumns
    {

        public static final Uri CONTENTURI=Uri.withAppendedPath(BASE_CONTENT_URI,PATH_MOVIES);

        public static final String TABLE_NAME="movies";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_MOVIE_NAME= "moviename";
        public static final String COLUMN_MOVIE_ID = "movieid";
        public static final String COLUMN_MOVIE_PHOTO= "photo";
        public static final String COLUMN_RELEASE_DATE = "releasedate";
        public static final String COLUMN_Rating = "rating";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_POSTER = "poster";
    }
}
