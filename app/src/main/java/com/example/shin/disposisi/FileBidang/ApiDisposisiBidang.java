package com.example.shin.disposisi.FileBidang;

import com.example.shin.disposisi.Server;
import com.example.shin.disposisi.Surat;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiDisposisiBidang {
    String BASE_URL = Server.IP;
    @GET("DisposisiBidang.php")
    Call<ResponDisposisiBidang> getData(@Query("NamaBidang") String NamaBidang);
}
