package com.example.shin.disposisi.FileKadis;

import com.example.shin.disposisi.Respon;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiMendisposisi {
    @GET("KadisMendisposisi.php")
    Call<Respon> Mendisposisi(@Query("NomorSurat") String NomorSurat, @Query("Bidang") String Bidang, @Query("IsiDisposisi") String IsiDisposisi);
}
