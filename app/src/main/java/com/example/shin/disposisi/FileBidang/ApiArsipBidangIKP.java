package com.example.shin.disposisi.FileBidang;

import com.example.shin.disposisi.Server;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiArsipBidangIKP {
    String BASE_URL = Server.IP;
    @GET("ArsipBidang.php")
    Call<List<DataArsipBidangIKP>> getData(@Query("NamaBidang") String NamaBidang);
}
