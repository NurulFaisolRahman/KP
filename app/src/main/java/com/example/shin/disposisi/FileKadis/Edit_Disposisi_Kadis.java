package com.example.shin.disposisi.FileKadis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shin.disposisi.FileLogin.ApiClientLogin;
import com.example.shin.disposisi.UpdateDisposisi;
import com.example.shin.disposisi.LihatSurat;
import com.example.shin.disposisi.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Disposisi_Kadis extends AppCompatActivity {

    Button EditDisposisi, TombolLihatSurat;
    EditText IsiDisposisiKadis;
    CheckBox Sekretaris,IKP,APTIKA,SDTIK;
    ApiMendisposisi apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disposisi_surat);

        EditDisposisi = findViewById(R.id.KadisMendisposisi);
        TombolLihatSurat = findViewById(R.id.LihatSurat);
        IsiDisposisiKadis = findViewById(R.id.IsiDisposisiKadis);
        Sekretaris = findViewById(R.id.BidangSekretaris);
        IKP = findViewById(R.id.BidangIKP);
        APTIKA = findViewById(R.id.BidangAptika);
        SDTIK = findViewById(R.id.BidangSD_TIK);
        apiInterface = ApiClientLogin.GetApiClient().create(ApiMendisposisi.class);

        String[] NamaBidang = RV_Adapter_Arsip_Kadis.BidangTujuan.split("/");
        for (int i = 0; i < NamaBidang.length; i++){
            if (NamaBidang[i].equals("sekretaris")){
                Sekretaris.setChecked(true);
            }
            else if (NamaBidang[i].equals("ikp")){
                IKP.setChecked(true);
            }
            else if (NamaBidang[i].equals("aptika")){
                APTIKA.setChecked(true);
            }
            else if (NamaBidang[i].equals("sdtik")){
                SDTIK.setChecked(true);
            }
        }
        IsiDisposisiKadis.setText(RV_Adapter_Arsip_Kadis.IsiDisposisi);

        EditDisposisi.setText("EDIT DISPOSISI");
        EditDisposisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String IsiDisposisi = IsiDisposisiKadis.getText().toString();
                if (IsiDisposisi.equals("") || (!Sekretaris.isChecked() && !IKP.isChecked() && !APTIKA.isChecked() && !SDTIK.isChecked())){
                    Toast.makeText(Edit_Disposisi_Kadis.this, "Mohon Lengkapi Input", Toast.LENGTH_SHORT).show();
                }
                else {
                    String BidangTujuan = "";
                    if (Sekretaris.isChecked()){
                        BidangTujuan += "sekretaris";
                    }
                    if (IKP.isChecked()){
                        BidangTujuan += "/ikp";
                    }
                    if (APTIKA.isChecked()){
                        BidangTujuan += "/aptika";
                    }
                    if (SDTIK.isChecked()){
                        BidangTujuan += "/sdtik";
                    }
                    KadisEditDisposisi(RV_Adapter_Arsip_Kadis.NomorSurat,BidangTujuan,IsiDisposisi);
                    finish();
                }
            }
        });

        TombolLihatSurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LihatSurat.NomorSurat = RV_Adapter_Arsip_Kadis.NomorSurat;
                Intent TombolLihatSurat = new Intent(Edit_Disposisi_Kadis.this, LihatSurat.class);
                startActivity(TombolLihatSurat);
            }
        });
    }

    private void KadisEditDisposisi(String NomorSurat, String Bidang, String IsiDisposisi){
        Call<UpdateDisposisi> call = apiInterface.Mendisposisi(NomorSurat,Bidang,IsiDisposisi);
        call.enqueue(new Callback<UpdateDisposisi>() {
            @Override
            public void onResponse(Call<UpdateDisposisi> call, Response<UpdateDisposisi> response) {
                if (response.body().getRespon().equals("sukses")){
                    Toast.makeText(Edit_Disposisi_Kadis.this, "Edit Disposisi Sukses", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateDisposisi> call, Throwable t) {
                Toast.makeText(Edit_Disposisi_Kadis.this, "Mohon Cek Koneksi Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
