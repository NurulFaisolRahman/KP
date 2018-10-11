package com.example.shin.disposisi.FileKadis;

import com.example.shin.disposisi.Server;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiDisposisiKadis {
    String BASE_URL = Server.IP;
    @GET("DisposisiKadis.php")
    Call<ResponDisposisiKadis> getData(@Query("status") String Status);
}
