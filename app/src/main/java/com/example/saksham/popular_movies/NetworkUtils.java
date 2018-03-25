package com.example.saksham.popular_movies;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saksham on 25-01-2018.
 */

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";


        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Movies> extractMovieFromJson(String moviesJSON) {

        if (TextUtils.isEmpty(moviesJSON)) {
            return null;
        }

        List<Movies> movies1 = new ArrayList<>();

        try {


            JSONObject baseJsonResponse = new JSONObject(moviesJSON);


            JSONArray moviesArray = baseJsonResponse.getJSONArray("results");

            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject currentMovie = moviesArray.getJSONObject(i);


                String poster_path = currentMovie.getString("poster_path");
                String origional_title = currentMovie.getString("original_title");
                String poster2_path = currentMovie.getString("backdrop_path");
                String overview = currentMovie.getString("overview");
                String vote_average = currentMovie.getString("vote_average");
                String release_date = currentMovie.getString("release_date");
                String movie_id=currentMovie.getString("id");

                Movies movie = new Movies(poster_path,origional_title,poster2_path,overview,vote_average,release_date,movie_id);
                movies1.add(movie);
            }
        } catch (JSONException e) {

            Log.e("NetworkUtils", "Problem parsing the JSON results", e);
        }
        return movies1;
    }

    public static List<Movies> fetchMoviesData(String requestUrl)
    {

        URL url = createUrl(requestUrl);


        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


        List<Movies> movies1 = extractMovieFromJson(jsonResponse);

        return movies1;
    }

    private static List<Review> extractReview(String reviewjason) {

        if (TextUtils.isEmpty(reviewjason)) {
            return null;
        }

        List<Review> moviesreview = new ArrayList<>();


        try {
            JSONObject baseJsonResponse = new JSONObject(reviewjason);
            JSONArray moviesArray = baseJsonResponse.getJSONArray("results");
            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject currentMovie = moviesArray.getJSONObject(i);
                String review = currentMovie.getString("content");
                String author= currentMovie.getString("author");

                Review review1=new Review(review,author);

                moviesreview.add(review1);
            }
        } catch (JSONException e) {

            Log.e("NetworkUtils", "Problem parsing the JSON results", e);
        }
        return moviesreview;
    }

    public static List<Review> fetchReviewData(String requestUrl)
    {

        URL url = createUrl(requestUrl);


        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


        List<Review> movies1 = extractReview(jsonResponse);

        return movies1;
    }

    private static List<Videos> extractVideos(String videojason) {

        if (TextUtils.isEmpty(videojason)) {
            return null;
        }

        List<Videos> moviesvideo= new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(videojason);
            JSONArray moviesArray = baseJsonResponse.getJSONArray("results");
            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject currentMovie = moviesArray.getJSONObject(i);

                String type = currentMovie.getString("type");

                   String  name=currentMovie.getString("name");
                   String trailer = currentMovie.getString("key");

                if(type.equalsIgnoreCase("Trailer"))
                {
                    Videos video1 = new Videos(trailer, name);
                    moviesvideo.add(video1);
                }
            }
        } catch (JSONException e) {

            Log.e("NetworkUtils", "Problem parsing the JSON results", e);
        }
        return moviesvideo;
    }

    public static List<Videos> fetchVideosData(String requestUrl)
    {

        URL url = createUrl(requestUrl);


        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<Videos> movies1 = extractVideos(jsonResponse);
        return movies1;
    }

}
