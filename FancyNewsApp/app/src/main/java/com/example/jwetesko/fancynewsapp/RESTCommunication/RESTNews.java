package com.example.jwetesko.fancynewsapp.RESTCommunication;

import com.example.jwetesko.fancynewsapp.Entities.ArticleList;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jwetesko on 09.01.17.
 */

public class RESTNews {

    private static final String BASE_NEWS_URL = "https://newsapi.org/v1/";

    public static Retrofit retrofitNews = new Retrofit.Builder()
            .baseUrl(BASE_NEWS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public interface NewsAPIInterface {

        @GET("articles")
        Call<ArticleList> getArticleList(@Query("source") String source, @Query("sortBy") String sortBy, @Query("apiKey") String apiKey);
    }
}
