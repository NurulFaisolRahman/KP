package com.example.shin.disposisi.FileOperator;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiDaftarFoto {
    @GET("DaftarFoto.php")
    Call<String[]> DaftarFoto(@Query("NomorSurat") String NomorSurat);
}
