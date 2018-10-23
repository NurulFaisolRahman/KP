package com.example.shin.disposisi.FileBidang;

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

public class Disposisi_Bidang extends Fragment{

    View v;
    private RecyclerView RV_Disposisi_Bidang;
    ApiDisposisiBidang apiDisposisiBidang;
    SwipeRefreshLayout Rafresh;
    String NamaBidang;
    Retrofit retrofit;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.rv_disposisi,container,false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        NamaBidang = sharedPreferences.getString("Nama","");
        RV_Disposisi_Bidang = v.findViewById(R.id.RV_Disposisi);
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiDisposisiBidang.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiDisposisiBidang = retrofit.create(ApiDisposisiBidang.class);
        Rafresh = v.findViewById(R.id.Rafresh);
        Rafresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TampilkanBidang();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Rafresh.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        if(getUserVisibleHint()){
            TampilkanBidang();
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getUserVisibleHint()){
            RV_Disposisi_Bidang.removeAllViewsInLayout();
            RV_Disposisi_Bidang.removeAllViewsInLayout();
            TampilkanBidang();
        }
    }

    private void TampilkanBidang(){
        Call<ResponDisposisiBidang> call = apiDisposisiBidang.getData(NamaBidang);
        call.enqueue(new Callback<ResponDisposisiBidang>() {
            @Override
            public void onResponse(Call<ResponDisposisiBidang> call, Response<ResponDisposisiBidang> response) {
                if (response.body().getRespon().equals("sukses")) {
                    RV_Adapter_Disposisi_Bidang RV_adapter = new RV_Adapter_Disposisi_Bidang(getContext(), response.body().getDataSurat());
                    RV_Disposisi_Bidang.setLayoutManager(new LinearLayoutManager(getActivity()));
                    RV_Disposisi_Bidang.setAdapter(RV_adapter);
                }
                else{
                    Toast.makeText(getContext(), "Belum Ada Surat Masuk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponDisposisiBidang> call, Throwable t) {
                Toast.makeText(getContext(), "Mohon Cek Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            TampilkanBidang();
        }
    }
}
