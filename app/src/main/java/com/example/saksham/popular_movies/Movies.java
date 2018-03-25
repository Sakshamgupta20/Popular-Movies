package com.example.saksham.popular_movies;

/**
 * Created by Saksham on 25-01-2018.
 */

public class Movies {

    private String imageUrl;
    private String title;
    private String detailposter;
    private String plot;
    private String raiting;
    private String releasedate;
    private String movieid;



    public Movies(String imageUrl, String title, String detailposter, String plot, String raiting, String releasedate ,String movieid) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.detailposter = detailposter;
        this.plot = plot;
        this.raiting = raiting;
        this.releasedate = releasedate;
        this.movieid=movieid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDetailposter() {
        return detailposter;
    }

    public String getPlot() {
        return plot;
    }

    public String getRaiting() {
        return raiting;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public String getMovieid() {
        return movieid;
    }
}
