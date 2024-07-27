package com.example.movies.model;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String type;
    @SerializedName("review")
    private String review;
    @SerializedName("author")
    private String author;

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getReview() {
        return review;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Review{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", author='" + author + '\'' +
                ", review='" + review + '\'' +
                '}';
    }

    public Review(String title, String type, String review, String author) {
        this.title = title;
        this.type = type;
        this.review = review;
        this.author = author;
    }
}
