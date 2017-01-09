package com.example.jwetesko.fancynewsapp.Entities;

import java.util.ArrayList;

/**
 * Created by jwetesko on 09.01.17.
 */

public class ArticleList {

    private String status;
    private String source;
    private String sortBy;
    private ArrayList<Article> articles;

    public ArticleList() {}

    public ArticleList(String status, String source, String sortBy, ArrayList<Article> articles) {
        this.status = status;
        this.source = source;
        this.sortBy = sortBy;
        this.articles = articles;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
