package com.example.shin.disposisi.FileBidang;

import com.example.shin.disposisi.FileKadis.ResponDisposisiKadis;
import com.example.shin.disposisi.Server;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiEditDisposisi {
    String BASE_URL = Server.IP;
    @GET("EditDisposisiSub.php")
    Call<ResponDisposisiKadis> EditDisposisi(@Query("NomorSurat") String NomorSurat, @Query("SubBidang") String SubBidang, @Query("NamaBidang") String NamaBidang);
}
