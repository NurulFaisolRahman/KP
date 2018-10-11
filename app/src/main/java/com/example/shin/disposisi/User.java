package com.example.shin.disposisi;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("respon")
    private String Respon;

    @SerializedName("nama")
    private String Nama;

    @SerializedName("tingkatan")
    private String Tingkatan;

    public String getResponse() {
        return Respon;
    }

    public String getName() {
        return Nama;
    }

    public String getTinkatan() {
        return Tingkatan;
    }
}
