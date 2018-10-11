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

public class EditDisposisiBidang extends AppCompatActivity {

    Button BidangMendisposisi, TombolLihatSurat;
    EditText IsiDisposisiKadis;
    TextView IsiDisposisi;
    CheckBox Sekretaris,IKP,APTIKA,SDTIK;
    ApiEditDisposisi apiEditDisposisi;
    String SubBidang = "";
    static String NomorSurat;
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
        IsiDisposisi = findViewById(R.id.IsiDisposisi);
        apiEditDisposisi = ApiClientLogin.GetApiClient().create(ApiEditDisposisi.class);
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        NamaBidang = sharedPreferences.getString("Nama","");
        IsiDisposisi.setVisibility(View.INVISIBLE);
        IsiDisposisiKadis.setVisibility(View.INVISIBLE);
        BidangMendisposisi.setText("EDIT DISPOSISI");

        if (NamaBidang.equals("ikp")){
            SDTIK.setVisibility(View.GONE);
            Sekretaris.setText("Seksi Pengelolaan Komunikasi Publik");
            IKP.setText("Seksi Pengelolaan Informasi Publik");
            APTIKA.setText("Seksi Pengelolaan Media Komunikasi");
            if (!RV_Adapter_Arsip_BidangIKP.SPKP.equals("")){
                Sekretaris.setChecked(true);
            }
            if (!RV_Adapter_Arsip_BidangIKP.SPIP.equals("")){
                IKP.setChecked(true);
            }
            if (!RV_Adapter_Arsip_BidangIKP.SPMK.equals("")){
                APTIKA.setChecked(true);
            }
        }
        else if (NamaBidang.equals("aptika")){
            SDTIK.setVisibility(View.GONE);
            Sekretaris.setText("Seksi Keamanan dan Persandian");
            IKP.setText("Seksi Pengundangan e-Goverment");
            APTIKA.setText("Seksi Infrastruktur Jaringan TIK");
            if (!RV_Adapter_Arsip_BidangAPTIKA.SKP.equals("")){
                Sekretaris.setChecked(true);
            }
            if (!RV_Adapter_Arsip_BidangAPTIKA.SPE.equals("")){
                IKP.setChecked(true);
            }
            if (!RV_Adapter_Arsip_BidangAPTIKA.SIJT.equals("")){
                APTIKA.setChecked(true);
            }
        }
        else if (NamaBidang.equals("sdtik")){
            SDTIK.setVisibility(View.GONE);
            Sekretaris.setText("Seksi Sumber Daya TIK");
            IKP.setText("Seksi Ekosistem TIK");
            APTIKA.setText("Seksi Data dan Statistik");
            if (!RV_Adapter_Arsip_BidangSDTIK.SST.equals("")){
                Sekretaris.setChecked(true);
            }
            if (!RV_Adapter_Arsip_BidangSDTIK.SET.equals("")){
                IKP.setChecked(true);
            }
            if (!RV_Adapter_Arsip_BidangSDTIK.SDS.equals("")){
                APTIKA.setChecked(true);
            }
        }

        TombolLihatSurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LihatSurat.NomorSurat = NomorSurat;
                Intent TombolLihatSurat = new Intent(EditDisposisiBidang.this, LihatSurat.class);
                startActivity(TombolLihatSurat);
            }
        });

        BidangMendisposisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NamaBidang.equals("ikp")){
                    if ((!Sekretaris.isChecked() && !IKP.isChecked() && !APTIKA.isChecked())){
                        Toast.makeText(EditDisposisiBidang.this, "Mohon Lengkapi Input", Toast.LENGTH_SHORT).show();
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
                        EditDisposisi();
                    }
                }else if(NamaBidang.equals("aptika")) {
                    if ((!Sekretaris.isChecked() && !IKP.isChecked() && !APTIKA.isChecked())) {
                        Toast.makeText(EditDisposisiBidang.this, "Mohon Lengkapi Input", Toast.LENGTH_SHORT).show();
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
                        EditDisposisi();
                    }
                }else if(NamaBidang.equals("sdtik")) {
                    if ((!Sekretaris.isChecked() && !IKP.isChecked() && !APTIKA.isChecked())) {
                        Toast.makeText(EditDisposisiBidang.this, "Mohon Lengkapi Input", Toast.LENGTH_SHORT).show();
                    } else {
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
                        EditDisposisi();
                    }
                }
            }
        });
    }

    private void EditDisposisi(){
        Call<ResponDisposisiKadis> call = apiEditDisposisi.EditDisposisi(NomorSurat,SubBidang,NamaBidang);
        call.enqueue(new Callback<ResponDisposisiKadis>() {
            @Override
            public void onResponse(Call<ResponDisposisiKadis> call, Response<ResponDisposisiKadis> response) {
                if (response.body().getRespon().equals("sukses")){
                    Toast.makeText(EditDisposisiBidang.this, "Edit Disposisi Sukses", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponDisposisiKadis> call, Throwable t) {
                Toast.makeText(EditDisposisiBidang.this, "Mohon Cek Koneksi Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
