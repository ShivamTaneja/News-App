package com.example.shivam.newsapp1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsList>>{

    private RecyclerView recyclerView;
    private TextView blank_screen_message;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    View loadingIndicator ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingIndicator =  findViewById(R.id.loading_spinner);
        recyclerView = findViewById(R.id.recycler_view);
        blank_screen_message = findViewById(R.id.blank_screen_message);
        int id = 0;

        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected())
        {
            getSupportLoaderManager().initLoader(id,null, this).forceLoad();
        }
        else
        {
            loadingIndicator.setVisibility(View.INVISIBLE);
            blank_screen_message.setVisibility(View.VISIBLE);
            blank_screen_message.setText(R.string.no_internet_connection);
        }
    }

    private void updateUi(final List<NewsList> newsLists ) {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        NewsAdapter mAdapter = new NewsAdapter(newsLists);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                NewsList newsList1 = newsLists.get(position);
                Uri newsUri = Uri.parse(newsList1.getWebUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    @NonNull
    @Override
    public Loader<List<NewsList>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsListLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsList>> loader, List<NewsList> data) {

        if (data == null) {
            if(networkInfo != null && networkInfo.isConnected())
            {
                loadingIndicator.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                blank_screen_message.setVisibility(View.VISIBLE);
                blank_screen_message.setText(R.string.no_news_found);
            }
            else
            {
                loadingIndicator.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                blank_screen_message.setVisibility(View.VISIBLE);
                blank_screen_message.setText(R.string.no_internet_connection);
            }
        }
        else
        {
            loadingIndicator.setVisibility(View.INVISIBLE);
            blank_screen_message.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            updateUi(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsList>> loader)
    {
        recyclerView.setAdapter(new NewsAdapter(null));
    }
}



