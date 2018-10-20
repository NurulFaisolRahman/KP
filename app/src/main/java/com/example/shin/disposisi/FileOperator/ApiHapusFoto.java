package com.example.shin.disposisi.FileOperator;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiHapusFoto {
    @GET("HapusFoto.php")
    Call<String> HapusFoto(@Query("PathFoto") String PathFoto);
}
