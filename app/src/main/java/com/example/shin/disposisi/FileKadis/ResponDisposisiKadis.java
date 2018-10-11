package com.example.shin.disposisi.FileKadis;

import com.example.shin.disposisi.Surat;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponDisposisiKadis {

    @SerializedName("respon")
    private String Respon;

    @SerializedName("data")
    private List<Surat> DataSurat;

    public String getRespon() {
        return Respon;
    }

    public List<Surat> getDataSurat() {
        return DataSurat;
    }
}
