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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Arsip_Bidang extends Fragment {

    View v;
    private RecyclerView RV_Arsip_Bidang;
    ApiArsipBidangSekretaris apiArsipBidangSekretaris;
    ApiArsipBidangIKP apiArsipBidangIKP;
    ApiArsipBidangAPTIKA apiArsipBidangAPTIKA;
    ApiArsipBidangSDTIK apiArsipBidangSDTIK;
    SwipeRefreshLayout Rafresh;
    String NamaBidang;
    Retrofit retrofitSekretaris;
    Retrofit retrofitIKP;
    Retrofit retrofitAPTIKA;
    Retrofit retrofitSDTIK;

    public Arsip_Bidang(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.rv_disposisi,container,false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        NamaBidang = sharedPreferences.getString("Nama","");
        RV_Arsip_Bidang = v.findViewById(R.id.RV_Disposisi);
        retrofitSekretaris= new Retrofit.Builder()
                .baseUrl(ApiArsipBidangSekretaris.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitIKP = new Retrofit.Builder()
                .baseUrl(ApiArsipBidangIKP.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPTIKA = new Retrofit.Builder()
                .baseUrl(ApiArsipBidangAPTIKA.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitSDTIK = new Retrofit.Builder()
                .baseUrl(ApiArsipBidangSDTIK.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiArsipBidangSekretaris = retrofitSekretaris.create(ApiArsipBidangSekretaris.class);
        apiArsipBidangIKP = retrofitIKP.create(ApiArsipBidangIKP.class);
        apiArsipBidangAPTIKA = retrofitAPTIKA.create(ApiArsipBidangAPTIKA.class);
        apiArsipBidangSDTIK = retrofitSDTIK.create(ApiArsipBidangSDTIK.class);
        Rafresh = v.findViewById(R.id.Rafresh);
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
        if(getUserVisibleHint()){
            TampilkanArsip();
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getUserVisibleHint()){
            RV_Arsip_Bidang.removeAllViewsInLayout();
            RV_Arsip_Bidang.removeAllViewsInLayout();
            TampilkanArsip();
        }
    }

    private void TampilkanArsip(){
        if (NamaBidang.equals("ikp")){
            Call<List<DataArsipBidangIKP>> call = apiArsipBidangIKP.getData(NamaBidang);
            call.enqueue(new Callback<List<DataArsipBidangIKP>>() {
                @Override
                public void onResponse(Call<List<DataArsipBidangIKP>> call, Response<List<DataArsipBidangIKP>> response) {
                    RV_Adapter_Arsip_BidangIKP RV_adapter = new RV_Adapter_Arsip_BidangIKP(getContext(), response.body());
                    RV_Arsip_Bidang.setLayoutManager(new LinearLayoutManager(getActivity()));
                    RV_Arsip_Bidang.setAdapter(RV_adapter);
                }

                @Override
                public void onFailure(Call<List<DataArsipBidangIKP>> call, Throwable t) {

                }
            });
        }
        else if (NamaBidang.equals("aptika")){
            Call<List<DataArsipBidangAPTIKA>> call = apiArsipBidangAPTIKA.getData(NamaBidang);
            call.enqueue(new Callback<List<DataArsipBidangAPTIKA>>() {
                @Override
                public void onResponse(Call<List<DataArsipBidangAPTIKA>> call, Response<List<DataArsipBidangAPTIKA>> response) {
                    RV_Adapter_Arsip_BidangAPTIKA RV_adapter = new RV_Adapter_Arsip_BidangAPTIKA(getContext(), response.body());
                    RV_Arsip_Bidang.setLayoutManager(new LinearLayoutManager(getActivity()));
                    RV_Arsip_Bidang.setAdapter(RV_adapter);
                }

                @Override
                public void onFailure(Call<List<DataArsipBidangAPTIKA>> call, Throwable t) {

                }
            });
        }
        else if (NamaBidang.equals("sdtik")){
            Call<List<DataArsipBidangSDTIK>> call = apiArsipBidangSDTIK.getData(NamaBidang);
            call.enqueue(new Callback<List<DataArsipBidangSDTIK>>() {
                @Override
                public void onResponse(Call<List<DataArsipBidangSDTIK>> call, Response<List<DataArsipBidangSDTIK>> response) {
                    RV_Adapter_Arsip_BidangSDTIK RV_adapter = new RV_Adapter_Arsip_BidangSDTIK(getContext(), response.body());
                    RV_Arsip_Bidang.setLayoutManager(new LinearLayoutManager(getActivity()));
                    RV_Arsip_Bidang.setAdapter(RV_adapter);
                }

                @Override
                public void onFailure(Call<List<DataArsipBidangSDTIK>> call, Throwable t) {

                }
            });
        }
        else if (NamaBidang.equals("sekretaris")){
            Call<List<DataArsipBidangSekretaris>> call = apiArsipBidangSekretaris.getData(NamaBidang);
            call.enqueue(new Callback<List<DataArsipBidangSekretaris>>() {
                @Override
                public void onResponse(Call<List<DataArsipBidangSekretaris>> call, Response<List<DataArsipBidangSekretaris>> response) {
                    RV_Adapter_Arsip_BidangSekretaris RV_adapter = new RV_Adapter_Arsip_BidangSekretaris(getContext(), response.body());
                    RV_Arsip_Bidang.setLayoutManager(new LinearLayoutManager(getActivity()));
                    RV_Arsip_Bidang.setAdapter(RV_adapter);
                }

                @Override
                public void onFailure(Call<List<DataArsipBidangSekretaris>> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            TampilkanArsip();
        }
    }
}
