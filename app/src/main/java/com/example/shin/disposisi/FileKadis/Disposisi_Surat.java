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
import com.example.shin.disposisi.LihatSurat;
import com.example.shin.disposisi.R;
import com.example.shin.disposisi.Respon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Disposisi_Surat extends AppCompatActivity {

    String NomorSurat;
    Button KadisMendisposisi, TombolLihatSurat;
    EditText IsiDisposisiKadis;
    CheckBox Sekretaris,IKP,APTIKA,SDTIK,BidangLain;
    ApiMendisposisi apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disposisi_surat);

        NomorSurat = RV_Adapter_Disposisi_Kadis.NomorSurat;
        KadisMendisposisi = findViewById(R.id.KadisMendisposisi);
        TombolLihatSurat = findViewById(R.id.LihatSurat);
        IsiDisposisiKadis = findViewById(R.id.IsiDisposisiKadis);
        Sekretaris = findViewById(R.id.BidangSekretaris);
        IKP = findViewById(R.id.BidangIKP);
        APTIKA = findViewById(R.id.BidangAptika);
        SDTIK = findViewById(R.id.BidangSD_TIK);
        BidangLain = findViewById(R.id.BidangLain);
        BidangLain.setVisibility(View.GONE);
        apiInterface = ApiClientLogin.GetApiClient().create(ApiMendisposisi.class);

        KadisMendisposisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String IsiDisposisi = IsiDisposisiKadis.getText().toString();
                if (IsiDisposisi.equals("") || (!Sekretaris.isChecked() && !IKP.isChecked() && !APTIKA.isChecked() && !SDTIK.isChecked())){
                    Toast.makeText(Disposisi_Surat.this, "Mohon Lengkapi Input", Toast.LENGTH_SHORT).show();
                }
                else {
                    String BidangTujuan = "";
                    if (Sekretaris.isChecked()){
                        BidangTujuan += "sekretaris";
                    }
                    if (IKP.isChecked()){
                        if (BidangTujuan.equals("")){
                            BidangTujuan += "ikp";
                        }else {
                            BidangTujuan += "/ikp";
                        }
                    }
                    if (APTIKA.isChecked()){
                        if (BidangTujuan.equals("")){
                            BidangTujuan += "aptika";
                        }else {
                            BidangTujuan += "/aptika";
                        }
                    }
                    if (SDTIK.isChecked()){
                        if (BidangTujuan.equals("")){
                            BidangTujuan += "sdtik";
                        }else {
                            BidangTujuan += "/sdtik";
                        }
                    }
                    DisposisiKadis(NomorSurat,BidangTujuan,IsiDisposisi);
                    finish();
                }
            }
        });

        TombolLihatSurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LihatSurat.NomorSurat = NomorSurat;
                Intent TombolLihatSurat = new Intent(Disposisi_Surat.this, LihatSurat.class);
                startActivity(TombolLihatSurat);
            }
        });
    }

    private void DisposisiKadis(String NomorSurat, String Bidang, String IsiDisposisi){
        Call<Respon> call = apiInterface.Mendisposisi(NomorSurat,Bidang,IsiDisposisi);
        call.enqueue(new Callback<Respon>() {
            @Override
            public void onResponse(Call<Respon> call, Response<Respon> response) {
                if (response.body().getRespon().equals("sukses")){
                    Toast.makeText(Disposisi_Surat.this, "Disposisi Sukses", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Respon> call, Throwable t) {
                Toast.makeText(Disposisi_Surat.this, "Mohon Cek Koneksi Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
