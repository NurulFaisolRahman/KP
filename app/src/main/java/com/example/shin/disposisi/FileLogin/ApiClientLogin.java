package com.example.shin.disposisi.FileLogin;

import com.example.shin.disposisi.Server;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientLogin {
    private static final String BASE_URL = Server.IP;
    private static Retrofit retrofit = null;

    public static Retrofit GetApiClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
