package com.example.shin.disposisi.FileSeksi;

import com.example.shin.disposisi.Respon;
import com.example.shin.disposisi.Server;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiUpdateSeksi {
    String BASE_URL = Server.IP;
    @GET("UpdateSeksi.php")
    Call<Respon> UpdateSeksi(@Query("NomorSurat") String NomorSurat, @Query("NamaBidang") String NamaBidang, @Query("SubBidang") String SubBidang);
}
