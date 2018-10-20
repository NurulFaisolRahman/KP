package com.example.shin.disposisi.FileBidang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.shin.disposisi.FileKadis.ResponDisposisiKadis;
import com.example.shin.disposisi.FileLogin.ApiClientLogin;
import com.example.shin.disposisi.LihatSurat;
import com.example.shin.disposisi.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BidangMendisposisi extends AppCompatActivity {

    Button BidangMendisposisi, TombolLihatSurat;
    EditText IsiDisposisiKadis;
    TextView IsiDisposisi;
    CheckBox Sekretaris,IKP,APTIKA,SDTIK,BidangLain;
    ApiBidangMendisposisi apiBidangMendisposisi;
    String SubBidang;
    String NamaBidang;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disposisi_surat);

        BidangMendisposisi = findViewById(R.id.KadisMendisposisi);
        TombolLihatSurat = findViewById(R.id.LihatSurat);
        IsiDisposisiKadis = findViewById(R.id.IsiDisposisiKadis);
        Sekretaris = findViewById(R.id.BidangSekretaris);
        IKP = findViewById(R.id.BidangIKP);
        APTIKA = findViewById(R.id.BidangAptika);
        SDTIK = findViewById(R.id.BidangSD_TIK);
        BidangLain = findViewById(R.id.BidangLain);
        IsiDisposisi = findViewById(R.id.IsiDisposisi);
        apiBidangMendisposisi = ApiClientLogin.GetApiClient().create(ApiBidangMendisposisi.class);

        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        NamaBidang = sharedPreferences.getString("Nama","");
        IsiDisposisi.setVisibility(View.INVISIBLE);
        IsiDisposisiKadis.setVisibility(View.INVISIBLE);

        if (NamaBidang.equals("ikp")){
            SDTIK.setVisibility(View.GONE);
            BidangLain.setVisibility(View.GONE);
            Sekretaris.setText("Seksi Pengelolaan Komunikasi Publik");
            IKP.setText("Seksi Pengelolaan Informasi Publik");
            APTIKA.setText("Seksi Pengelolaan Media Komunikasi");
        }
        else if (NamaBidang.equals("aptika")){
            SDTIK.setVisibility(View.GONE);
            BidangLain.setVisibility(View.GONE);
            Sekretaris.setText("Seksi Keamanan dan Persandian");
            IKP.setText("Seksi Pengundangan e-Goverment");
            APTIKA.setText("Seksi Infrastruktur Jaringan TIK");
        }
        else if (NamaBidang.equals("sdtik")){
            SDTIK.setVisibility(View.GONE);
            BidangLain.setVisibility(View.GONE);
            Sekretaris.setText("Seksi Sumber Daya TIK");
            IKP.setText("Seksi Ekosistem TIK");
            APTIKA.setText("Seksi Data dan Statistik");
        }
        else if (NamaBidang.equals("sekretaris")){
            Sekretaris.setText("Seksi Perencanaan Dan Keuangan");
            IKP.setText("Seksi Umum Dan Kepegawaian");
            BidangLain.setText("Bidang IKP");
        }

        TombolLihatSurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LihatSurat.NomorSurat = RV_Adapter_Disposisi_Bidang.NomorSurat;
                Intent TombolLihatSurat = new Intent(BidangMendisposisi.this, LihatSurat.class);
                startActivity(TombolLihatSurat);
            }
        });

        BidangMendisposisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NamaBidang.equals("ikp")){
                    if ((!Sekretaris.isChecked() && !IKP.isChecked() && !APTIKA.isChecked())){
                        Toast.makeText(BidangMendisposisi.this, "Mohon Lengkapi Input", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SubBidang = "";
                        if (Sekretaris.isChecked()) {
                            SubBidang += "spkp";
                        }
                        if (IKP.isChecked()) {
                            if (SubBidang.equals("")){
                                SubBidang += "spip";
                            }
                            else{
                                SubBidang += "/spip";
                            }
                        }
                        if (APTIKA.isChecked()) {
                            if (SubBidang.equals("")){
                                SubBidang += "spmk";
                            }
                            else{
                                SubBidang += "/spmk";
                            }
                        }
                        Disposisi();
                    }
                }else if(NamaBidang.equals("sdtik")){
                    if ((!Sekretaris.isChecked() && !IKP.isChecked() && !APTIKA.isChecked())){
                        Toast.makeText(BidangMendisposisi.this, "Mohon Lengkapi Input", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SubBidang = "";
                        if (Sekretaris.isChecked()) {
                            SubBidang += "sst";
                        }
                        if (IKP.isChecked()) {
                            if (SubBidang.equals("")){
                                SubBidang += "set";
                            }
                            else{
                                SubBidang += "/set";
                            }
                        }
                        if (APTIKA.isChecked()) {
                            if (SubBidang.equals("")){
                                SubBidang += "sds";
                            }
                            else{
                                SubBidang += "/sds";
                            }
                        }
                        Disposisi();
                    }
                }else if(NamaBidang.equals("aptika")) {
                    if ((!Sekretaris.isChecked() && !IKP.isChecked() && !APTIKA.isChecked())) {
                        Toast.makeText(BidangMendisposisi.this, "Mohon Lengkapi Input", Toast.LENGTH_SHORT).show();
                    } else {
                        SubBidang = "";
                        if (Sekretaris.isChecked()) {
                            SubBidang += "skp";
                        }
                        if (IKP.isChecked()) {
                            if (SubBidang.equals("")){
                                SubBidang += "spe";
                            }
                            else{
                                SubBidang += "/spe";
                            }
                        }
                        if (APTIKA.isChecked()) {
                            if (SubBidang.equals("")){
                                SubBidang += "sijt";
                            }
                            else{
                                SubBidang += "/sijt";
                            }
                        }
                        Disposisi();
                    }
                }else if(NamaBidang.equals("sekretaris")) {
                    if ((!Sekretaris.isChecked() && !IKP.isChecked() && !APTIKA.isChecked() && !SDTIK.isChecked() && !BidangLain.isChecked())) {
                        Toast.makeText(BidangMendisposisi.this, "Mohon Lengkapi Input", Toast.LENGTH_SHORT).show();
                    } else {
                        SubBidang = "";
                        if (Sekretaris.isChecked()) {
                            SubBidang += "spk";
                        }
                        if (IKP.isChecked()) {
                            if (SubBidang.equals("")){
                                SubBidang += "suk";
                            }
                            else{
                                SubBidang += "/suk";
                            }
                        }
                        if (APTIKA.isChecked()) {
                            if (SubBidang.equals("")){
                                SubBidang += "aptika";
                            }
                            else{
                                SubBidang += "/aptika";
                            }
                        }
                        if (SDTIK.isChecked()) {
                            if (SubBidang.equals("")){
                                SubBidang += "sdtik";
                            }
                            else{
                                SubBidang += "/sdtik";
                            }
                        }
                        if (BidangLain.isChecked()) {
                            if (SubBidang.equals("")){
                                SubBidang += "ikp";
                            }
                            else{
                                SubBidang += "/ikp";
                            }
                        }
                        Disposisi();
                    }
                }
            }
        });
    }

    private void Disposisi(){
        Call<ResponDisposisiKadis> call = apiBidangMendisposisi.DisposisiSub(RV_Adapter_Disposisi_Bidang.NomorSurat,SubBidang,NamaBidang);
        call.enqueue(new Callback<ResponDisposisiKadis>() {
            @Override
            public void onResponse(Call<ResponDisposisiKadis> call, Response<ResponDisposisiKadis> response) {
                if (response.body().getRespon().equals("sukses")){
                    Toast.makeText(BidangMendisposisi.this, "Disposisi Sukses", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponDisposisiKadis> call, Throwable t) {
                Toast.makeText(BidangMendisposisi.this, "Mohon Cek Koneksi Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
