package com.example.shin.disposisi.FileOperator;

import com.example.shin.disposisi.FormDisposisi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiDisposisiOperator {
    @GET("DisposisiOperator.php")
    Call<FormDisposisi> DisposisiOperator(@Query("nomor_surat") String nomor_surat, @Query("surat_dari") String surat_dari, @Query("tanggal_surat") String tanggal_surat, @Query("diterima_tanggal") String diterima_tanggal, @Query("nomor_agenda") String nomor_agenda, @Query("sifat") String sifat, @Query("perihal") String perihal);
}
