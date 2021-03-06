package com.example.shashikiran.newsapp;

/**
 * Created by 2rite on 6/26/2017.
 */

public class NewsItems {
    private String title, author, description, publishedAt, url;

    public NewsItems(String title, String author, String description, String publishedAt, String url){
        this.title = title;
        this.author = author;
        this.description = description;
        this.publishedAt = publishedAt;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}
