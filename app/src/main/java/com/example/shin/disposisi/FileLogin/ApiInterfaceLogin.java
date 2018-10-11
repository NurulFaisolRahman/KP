package com.example.shin.disposisi.FileLogin;

import com.example.shin.disposisi.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaceLogin {
    @GET("Login.php")
    Call<User> performUserLogin(@Query("nama") String nama, @Query("sandi") String sandi);
}
