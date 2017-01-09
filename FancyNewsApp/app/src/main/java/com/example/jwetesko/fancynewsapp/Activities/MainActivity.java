package com.example.jwetesko.fancynewsapp.Activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.jwetesko.fancynewsapp.Adapters.NewsAdapter;
import com.example.jwetesko.fancynewsapp.Entities.ArticleList;
import com.example.jwetesko.fancynewsapp.Entities.Contact;
import com.example.jwetesko.fancynewsapp.Entities.ContactList;
import com.example.jwetesko.fancynewsapp.R;
import com.example.jwetesko.fancynewsapp.RESTCommunication.RESTCommunication;
import com.example.jwetesko.fancynewsapp.RESTCommunication.RESTNews;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.jwetesko.fancynewsapp.Activities.MainActivity.MsgReceiver.ACTION_NEW_MSG;

public class MainActivity extends AppCompatActivity {

    private Context context = this;

    public ContactList contactlist;
    private RESTCommunication.MyApiEndpointInterface apiService = RESTCommunication.retrofit.create(RESTCommunication.MyApiEndpointInterface.class);
    private RESTNews.NewsAPIInterface apiNewsService = RESTNews.retrofitNews.create(RESTNews.NewsAPIInterface.class);

    private MsgReceiver msgReceiver;
    private ArrayList<String> msgReceived = new ArrayList<>();
    private ArticleList aList = new ArticleList();

    public static final String FANCY_PREFS = "fancySharedPreferences";
    public static final String SP_NEW_MESSAGE = "sharedPreferences";
    private static final String API_KEY = "63389262eabc4e7eae7924be13081ee1";
    private static final String SOURCE = "bbc-news";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initReceiver();

        Button startBtn = (Button) findViewById(R.id.start_button);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* get news  from BBC */
                Call<ArticleList> articleListCall = apiNewsService.getArticleList(SOURCE, "top",API_KEY);
                articleListCall.enqueue(new Callback<ArticleList>() {

                    @Override
                    public void onResponse(Call<ArticleList> articleList, Response<ArticleList> response) {
                        System.out.println(response.code());
                        System.out.println("News OK");
                        aList = response.body();
                        Log.d("CallBack", new Gson().toJson(response.body()));
                        initUI();
                    }

                    @Override
                    public void onFailure(Call<ArticleList> call, Throwable t) {
                        System.out.println(t.getMessage());
                        System.out.println("News Fail");
                    }
                });

                /* get Contacts from SP or BR */

                if (msgReceived.size() == 0) {
                    Log.d("USING SHARED PREF", Integer.toString(msgReceived.size()));
                    getFromSharedPreferences();
                }
                else {
                    Log.d("USING BROADCAST", Integer.toString(msgReceived.size()));
                }

                /* translating message to contacts list */
                msgToContacts();

                /* post contact list */
                Call<ContactList> contactListCall = apiService.createContactList(contactlist);
                contactListCall.enqueue(new Callback<ContactList>() {

                    @Override
                    public void onResponse(Call<ContactList> call, Response<ContactList> response) {
                        System.out.println(response.code());
                        System.out.println("OK");
                    }

                    @Override
                    public void onFailure(Call<ContactList> call, Throwable t) {
                        System.out.println(t.getMessage());
                        System.out.println("Fail");
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        finishReceiver();
        super.onDestroy();
    }

    private void initUI() {
        if (aList.getArticles().size() > 0) {
            ListView newsListView = (ListView) findViewById(R.id.news_list);
            NewsAdapter newsAdapter = new NewsAdapter(aList.getArticles(), context);
            newsListView.setAdapter(newsAdapter);
        }
        else {
            System.out.println("No articles loaded");
        }
    }

    private void msgToContacts() {

        if (msgReceived != null && msgReceived.size() > 0 ) {
            int i = 0;
            ArrayList<Contact> ctList = new ArrayList<>();

            for (String msg: msgReceived) {
                String[] tokens = msg.split("--");
                Contact ct = new Contact(i, tokens[0], tokens[1]);
                ctList.add(ct);
                i++;
            }
            contactlist = new ContactList(ctList);
        }
    }

    private void getFromSharedPreferences() {
        Context sharedContext = null;
        try {
            sharedContext = this.createPackageContext("com.example.jwetesko.fancyaddressbook", Context.CONTEXT_RESTRICTED);
            if (sharedContext == null) { return; }
            SharedPreferences sharedPrefs = sharedContext.getSharedPreferences(FANCY_PREFS, Activity.MODE_PRIVATE);

            Gson gson = new Gson();
            String json = sharedPrefs.getString(SP_NEW_MESSAGE, null);
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            msgReceived = gson.fromJson(json, type);

            if (msgReceived != null) {
                Log.d("SHARED PREFERENCES", Integer.toString(msgReceived.size()));
            }

        } catch (Exception e) {
            String error = e.getMessage();
            Log.d("SHARED PREFS ERROR", error);
            return;
        }
    }

    private void initReceiver() {
        msgReceiver = new MsgReceiver();
        IntentFilter filter = new IntentFilter(ACTION_NEW_MSG);
        registerReceiver(msgReceiver, filter);
    }

    private void finishReceiver() {
        unregisterReceiver(msgReceiver);
    }

    public class MsgReceiver extends BroadcastReceiver {

        public static final String ACTION_NEW_MSG = "broadcastReceiver";
        public static final String MSG_FIELD = "message";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_NEW_MSG)) {
                msgReceived = intent.getStringArrayListExtra(MSG_FIELD);
                Log.d("FROM BROADCAST RECEIVER", Integer.toString(msgReceived.size()) + " contacts");
            }
        }
    }
}