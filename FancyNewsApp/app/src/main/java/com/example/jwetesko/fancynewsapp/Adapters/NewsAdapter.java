package com.example.jwetesko.fancynewsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jwetesko.fancynewsapp.Entities.Article;
import com.example.jwetesko.fancynewsapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jwetesko on 09.01.17.
 */

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<Article> articles = new ArrayList<>();

    public NewsAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.articles.size();
    }

    @Override
    public Article getItem(int position) {
        return this.articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newsRow;

        if (convertView == null) {
            newsRow = LayoutInflater.from(context).inflate(R.layout.news_list_row, parent, false);
        } else {
            newsRow = convertView;
        }

        bindContactsToView(getItem(position), newsRow);
        return newsRow;
    }

    private void bindContactsToView(Article article, View newsRow) {

        TextView titleTV = (TextView) newsRow.findViewById(R.id.news_title);
        titleTV.setText(article.getTitle());

        TextView authorTV = (TextView) newsRow.findViewById(R.id.news_author);
        authorTV.setText(article.getAuthor());

        TextView descriptionTV = (TextView) newsRow.findViewById(R.id.news_description);
        descriptionTV.setText(article.getDescription());
    }
}