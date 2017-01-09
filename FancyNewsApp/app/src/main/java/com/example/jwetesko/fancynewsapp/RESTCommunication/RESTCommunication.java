package com.example.jwetesko.fancynewsapp.RESTCommunication;

import com.example.jwetesko.fancynewsapp.Entities.ContactList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by jwetesko on 06.01.17.
 */

public class RESTCommunication {

    public static final String BASE_URL = "http://104.236.74.232/app.php/";

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public interface MyApiEndpointInterface {

        @POST("contactlist")
        Call<ContactList> createContactList(@Body ContactList contactList);

    }
}