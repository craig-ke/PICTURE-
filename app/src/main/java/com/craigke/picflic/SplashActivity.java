package com.craigke.picflic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.craigke.picflic.adapters.ListImageAdapter;
import com.craigke.picflic.api.ApiClient;
import com.craigke.picflic.api.UnsplashInterface;
import com.craigke.picflic.model.Result;
import com.craigke.picflic.model.UnsplashAPIResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentAddress;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private ListImageAdapter mAdapter;
    private List<Result> results;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getRestaurants(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        UnsplashInterface client= ApiClient.getClient().create(UnsplashInterface.class);
        Call<UnsplashAPIResponse> call = client.getSearchPhotos("London", Constants.UNSPLASH_API);

        call.enqueue(new Callback<UnsplashAPIResponse>() {
            @Override
            public void onResponse(Call<UnsplashAPIResponse> call, Response<UnsplashAPIResponse> response) {
                hideProgressBar();
                if(response.isSuccessful()){
                    results = response.body().getResults();
                    mAdapter = new ListImageAdapter(SplashActivity.this, results);
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(SplashActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

                    showResults();

                }
            }

            @Override
            public void onFailure(Call<UnsplashAPIResponse> call, Throwable t) {
                hideProgressBar();
                showFailureMessage();

            }
            private void showResults() {
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        });
    }


    private void showFailureMessage() {

    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);

    }

}

