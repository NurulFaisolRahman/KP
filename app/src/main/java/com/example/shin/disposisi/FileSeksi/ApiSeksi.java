package com.example.shin.disposisi.FileSeksi;

import com.example.shin.disposisi.Server;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiSeksi {
    String BASE_URL = Server.IP;
    @GET("DisposisiSeksi.php")
    Call<ResponSeksi> getData(@Query("NamaBidang") String NamaBidang, @Query("NamaSub") String NamaSub, @Query("Status") String Status);
}
