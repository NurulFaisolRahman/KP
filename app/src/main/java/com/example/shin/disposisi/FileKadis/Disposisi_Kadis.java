package com.example.shin.disposisi.FileKadis;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.shin.disposisi.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Disposisi_Kadis extends Fragment {

    View v;
    private RecyclerView RV_Disposisi_Kadis;
    RV_Adapter_Disposisi_Kadis RV_adapter;
    ApiDisposisiKadis apiDisposisiKadis;
    SwipeRefreshLayout Rafresh;
    Retrofit retrofit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.rv_disposisi,container,false);
        RV_Disposisi_Kadis = v.findViewById(R.id.RV_Disposisi);
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiDisposisiKadis.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiDisposisiKadis = retrofit.create(ApiDisposisiKadis.class);
        Rafresh = v.findViewById(R.id.Rafresh);
        Rafresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TampilkanDisposisi();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Rafresh.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        RV_Disposisi_Kadis.removeAllViewsInLayout();
        RV_Disposisi_Kadis.removeAllViewsInLayout();
        TampilkanDisposisi();
    }

    private void TampilkanDisposisi(){
        Call<ResponDisposisiKadis> call = apiDisposisiKadis.getData("");
        call.enqueue(new Callback<ResponDisposisiKadis>() {
            @Override
            public void onResponse(Call<ResponDisposisiKadis> call, Response<ResponDisposisiKadis> response) {
                if (response.body().getRespon().equals("sukses")){
                    RV_adapter = new RV_Adapter_Disposisi_Kadis(getContext(),response.body().getDataSurat());
                    RV_Disposisi_Kadis.setLayoutManager(new LinearLayoutManager(getActivity()));
                    RV_Disposisi_Kadis.setAdapter(RV_adapter);
                }
                else {
                    Toast.makeText(getContext(), "Belum Ada Surat Masuk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponDisposisiKadis> call, Throwable t) {
                Toast.makeText(getContext(), "Mohon Cek Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
