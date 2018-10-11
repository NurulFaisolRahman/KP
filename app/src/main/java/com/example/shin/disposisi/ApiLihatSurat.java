package com.example.shin.disposisi;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiLihatSurat {
    String BASE_URL = Server.IP;
    @GET("LihatSurat.php")
    Call<String[]> getData(@Query("NomorSurat") String NomorSurat);
}
