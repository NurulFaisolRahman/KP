package com.example.shin.disposisi.FileSeksi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shin.disposisi.FileKadis.ResponDisposisiKadis;
import com.example.shin.disposisi.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArsipSeksi extends Fragment {

    View v;
    private RecyclerView RV_Seksi;
    SwipeRefreshLayout Rafresh;
    Retrofit retrofit;
    ApiSeksi apiSeksi;
    String NamaBidang,NamaSub;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.rv_disposisi, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        NamaSub = sharedPreferences.getString("Nama","");
        if (NamaSub.equals("spkp") || NamaSub.equals("spip") || NamaSub.equals("spmk")){
            NamaBidang = "ikp";
        }
        else if (NamaSub.equals("sst") || NamaSub.equals("set") || NamaSub.equals("sds")){
            NamaBidang = "sdtik";
        }
        else if (NamaSub.equals("skp") || NamaSub.equals("spe") || NamaSub.equals("sijt")){
            NamaBidang = "aptika";
        }
        RV_Seksi = v.findViewById(R.id.RV_Disposisi);
        Rafresh = v.findViewById(R.id.Rafresh);
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiSeksi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiSeksi = retrofit.create(ApiSeksi.class);
        Rafresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TampilkanArsip();
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
        RV_Seksi.removeAllViewsInLayout();
        RV_Seksi.removeAllViewsInLayout();
        TampilkanArsip();
    }

    private void TampilkanArsip(){
        Call<ResponDisposisiKadis> call = apiSeksi.getData(NamaBidang,NamaSub,"1");
        call.enqueue(new Callback<ResponDisposisiKadis>() {
            @Override
            public void onResponse(Call<ResponDisposisiKadis> call, Response<ResponDisposisiKadis> response) {
                if (response.body().getRespon().equals("sukses")){
                    RV_Adapter_Arsip_Seksi RV_adapter = new RV_Adapter_Arsip_Seksi(getContext(), response.body().getDataSurat());
                    RV_Seksi.setLayoutManager(new LinearLayoutManager(getActivity()));
                    RV_Seksi.setAdapter(RV_adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponDisposisiKadis> call, Throwable t) {

            }
        });
    }
}
