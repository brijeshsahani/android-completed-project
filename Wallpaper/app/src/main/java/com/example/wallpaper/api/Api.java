package com.example.wallpaper.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Api {

    @GET("4KWallpaper.php")
    Call<Object> getwallpaper(
            @Header("key") String email,
            @Header("Authorization") String authorization);





}

