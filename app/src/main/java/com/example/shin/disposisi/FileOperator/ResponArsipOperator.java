package com.example.shin.disposisi.FileOperator;

import com.example.shin.disposisi.Surat;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponArsipOperator {

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
