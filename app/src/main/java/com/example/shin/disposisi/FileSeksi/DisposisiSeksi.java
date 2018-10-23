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
import android.widget.Toast;

import com.example.shin.disposisi.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisposisiSeksi extends Fragment {

    View v;
    private RecyclerView RV_Seksi;
    SwipeRefreshLayout Rafresh;
    Retrofit retrofit;
    ApiSeksi apiSeksi;
    String NamaBidang,NamaSub;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.rv_disposisi,container,false);
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
        else if (NamaSub.equals("spk") || NamaSub.equals("suk")){
            NamaBidang = "sekretaris";
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
                TampilkanSurat();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Rafresh.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        if(getUserVisibleHint()){
            TampilkanSurat();
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getUserVisibleHint()){
            RV_Seksi.removeAllViewsInLayout();
            RV_Seksi.removeAllViewsInLayout();
            TampilkanSurat();
        }
    }

    private void TampilkanSurat(){
        Call<ResponSeksi> call = apiSeksi.getData(NamaBidang,NamaSub,"0");
        call.enqueue(new Callback<ResponSeksi>() {
            @Override
            public void onResponse(Call<ResponSeksi> call, Response<ResponSeksi> response) {
                if (response.body().getRespon().equals("sukses")){
                    RV_Adapter_Seksi RV_adapter = new RV_Adapter_Seksi(getContext(), response.body().getDataSurat());
                    RV_Seksi.setLayoutManager(new LinearLayoutManager(getActivity()));
                    RV_Seksi.setAdapter(RV_adapter);
                }
                else {
                    Toast.makeText(getContext(), "Belum Ada Surat Masuk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponSeksi> call, Throwable t) {
                Toast.makeText(getContext(), "Mohon Cek Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            TampilkanSurat();
        }
    }
}
