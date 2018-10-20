package com.example.shin.disposisi.FileOperator;

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
import com.example.shin.disposisi.Surat;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Arsip_Operator extends Fragment {

    View v;
    private RecyclerView RV_Arsip_Operator;
    SwipeRefreshLayout Rafresh;
    Retrofit retrofit;
    ApiArsipOperator apiArsipOperator;

    public Arsip_Operator() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.rv_disposisi,container,false);
        RV_Arsip_Operator = v.findViewById(R.id.RV_Disposisi);
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiArsipOperator.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiArsipOperator = retrofit.create(ApiArsipOperator.class);
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

    private void TampilkanArsip(){
        Call<ResponArsipOperator> call = apiArsipOperator.getData();
        call.enqueue(new Callback<ResponArsipOperator>() {
            @Override
            public void onResponse(Call<ResponArsipOperator> call, Response<ResponArsipOperator> response) {
                if (response.body().getRespon().equals("sukses")){
                    RV_Adapter_Arsip_Operator RV_adapter = new RV_Adapter_Arsip_Operator(getContext(), response.body().getDataSurat());
                    RV_Arsip_Operator.setLayoutManager(new LinearLayoutManager(getActivity()));
                    RV_Arsip_Operator.setAdapter(RV_adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponArsipOperator> call, Throwable t) {
                Toast.makeText(getContext(), "Mohon Cek Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        RV_Arsip_Operator.removeAllViewsInLayout();
        RV_Arsip_Operator.removeAllViewsInLayout();
        TampilkanArsip();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            TampilkanArsip();
        }
    }
}
