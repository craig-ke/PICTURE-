package com.craigke.picflic.api;

import com.craigke.picflic.model.UnsplashAPIResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UnsplashInterface {
    @GET("/search/photos")
    Call<UnsplashAPIResponse> getSearchPhotos(
            @Query("query") String searchTerm,
            @Query("client_id") String apiKey
    );
}

//    @GET("/search/photos")
//    Call<List<UnsplashAPIResponse>> getSearchPhotos(
//            @Query("client_id") String apiKey,
//            @Query("per_page") int perPage,
//            @Query("query") String searchTerm
//    );
//}
