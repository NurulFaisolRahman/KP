package com.example.shin.disposisi;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LihatSurat extends AppCompatActivity {

    private String[] DaftarLink;
    public static String NomorSurat;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lihat_surat);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLihatSurat.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiLihatSurat apiLihatSurat = retrofit.create(ApiLihatSurat.class);
        Call<String[]> call = apiLihatSurat.getData(NomorSurat);
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                DaftarLink = response.body();
                ViewPager viewPager = findViewById(R.id.view_pager);
                ViewPagerAdapterFotoSurat adapter = new ViewPagerAdapterFotoSurat(LihatSurat.this, DaftarLink);
                viewPager.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                Toast.makeText(LihatSurat.this, "Mohon Cek Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
