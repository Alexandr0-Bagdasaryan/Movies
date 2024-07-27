package com.example.movies.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Poster implements Serializable {
    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public Poster(String url) {
        this.url = url;
    }

    public static String defaultPoster="https://st.kp.yandex.net/images/no-poster.gif";

    @Override
    public String toString() {
        return "Poster{" +
                "url='" + url + '\'' +
                '}';
    }
}
