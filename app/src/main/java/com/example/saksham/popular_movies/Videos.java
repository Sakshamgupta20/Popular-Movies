package com.example.saksham.popular_movies;

/**
 * Created by Saksham on 06-02-2018.
 */

public class Videos {
        private String key;
        private String name;

    public Videos(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}