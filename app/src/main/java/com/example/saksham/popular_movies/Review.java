package com.example.saksham.popular_movies;

/**
 * Created by Saksham on 05-02-2018.
 */

public class Review {
 private String review;
 private String author;

    public Review(String review, String author) {
        this.review = review;
        this.author = author;
    }

    public String getReview() {
        return review;
    }

    public String getAuthor() {
        return author;
    }
}
