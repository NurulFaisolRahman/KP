package com.example.shin.disposisi.FileOperator;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.shin.disposisi.FileKadis.ApiMendisposisi;
import com.example.shin.disposisi.FileLogin.ApiClientLogin;
import com.example.shin.disposisi.FormDisposisi;
import com.example.shin.disposisi.R;
import com.example.shin.disposisi.Server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSurat extends AppCompatActivity {

    EditText SuratDari,TanggalSurat,NomorSurat,DiterimaTanggal,NomorAgenda,Perihal;
    RadioGroup EditSifat;
    RadioButton SangatSegera,Segera,Rahasia,Biasa,Pilihan;
    Button Cetak,Edit,Simpan;
    DownloadManager Download;
    ApiEditSurat apiEditSurat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_surat);

        SuratDari = findViewById(R.id.surat_dari);
        TanggalSurat = findViewById(R.id.tanggal_surat);
        NomorSurat = findViewById(R.id.nomor_surat);
        DiterimaTanggal = findViewById(R.id.diterima_tanggal);
        NomorAgenda = findViewById(R.id.nomor_agenda);
        EditSifat = findViewById(R.id.EditSifat);
        Perihal = findViewById(R.id.perihal);
        Cetak = findViewById(R.id.Cetak);
        Edit = findViewById(R.id.Edit);
        Simpan = findViewById(R.id.Simpan);
        Simpan.setVisibility(View.GONE);
        SangatSegera =  findViewById(R.id.SangatSegera);
        Segera =  findViewById(R.id.Segera);
        Rahasia =  findViewById(R.id.Rahasia);
        Biasa =  findViewById(R.id.Biasa);
        SangatSegera.setEnabled(false);
        Segera.setEnabled(false);
        Rahasia.setEnabled(false);
        Biasa.setEnabled(false);
        EditSifat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Pilihan = findViewById(EditSifat.getCheckedRadioButtonId());
            }
        });
        apiEditSurat = ApiClientLogin.GetApiClient().create(ApiEditSurat.class);

        SuratDari.setText(RV_Adapter_Arsip_Operator.SuratDari);
        TanggalSurat.setText(RV_Adapter_Arsip_Operator.TanggalSurat);
        NomorSurat.setText(RV_Adapter_Arsip_Operator.NomorSurat);
        DiterimaTanggal.setText(RV_Adapter_Arsip_Operator.DiterimaTanggal);
        NomorAgenda.setText(RV_Adapter_Arsip_Operator.NomorAgenda);
        if (RV_Adapter_Arsip_Operator.Sifat.equals("Sangat Segera")){
            EditSifat.check(R.id.SangatSegera);
        }
        else if (RV_Adapter_Arsip_Operator.Sifat.equals("Segera")){
            EditSifat.check(R.id.Segera);
        }
        else if (RV_Adapter_Arsip_Operator.Sifat.equals("Rahasia")){
            EditSifat.check(R.id.Rahasia);
        }
        else if (RV_Adapter_Arsip_Operator.Sifat.equals("Biasa")){
            EditSifat.check(R.id.Biasa);
        }

        Perihal.setText(RV_Adapter_Arsip_Operator.Perihal);

        if (!RV_Adapter_Arsip_Operator.Status.equals("Disposisi")) {
            Cetak.setVisibility(View.INVISIBLE);
        }

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit.setVisibility(View.GONE);
                Simpan.setVisibility(View.VISIBLE);
                Cetak.setVisibility(View.GONE);
                SuratDari.setEnabled(true);
                TanggalSurat.setEnabled(true);
                DiterimaTanggal.setEnabled(true);
                NomorAgenda.setEnabled(true);
                SangatSegera.setEnabled(true);
                Segera.setEnabled(true);
                Rahasia.setEnabled(true);
                Biasa.setEnabled(true);
                Perihal.setEnabled(true);
            }
        });

        Cetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date Tanggal = Calendar.getInstance().getTime();
                SimpleDateFormat FormatTanggal = new SimpleDateFormat("dd-MMM-yyyy");
                String TanggalDiposisi = FormatTanggal.format(Tanggal);
                Download = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(Server.IP + "CetakPDF.php?NomorSurat=" + RV_Adapter_Arsip_Operator.NomorSurat + "&Tanggal=" + TanggalDiposisi);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Disposisi_" + RV_Adapter_Arsip_Operator.NomorSurat + ".pdf");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Download.enqueue(request);
            }
        });

        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<FormDisposisi> call = apiEditSurat.EditSurat(NomorSurat.getText().toString(),SuratDari.getText().toString(),TanggalSurat.getText().toString(),DiterimaTanggal.getText().toString(),NomorAgenda.getText().toString(),Pilihan.getText().toString(),Perihal.getText().toString());
                call.enqueue(new Callback<FormDisposisi>() {
                    @Override
                    public void onResponse(Call<FormDisposisi> call, Response<FormDisposisi> response) {
                        if (response.body().getRespon().equals("sukses")){
                            Toast.makeText(DetailSurat.this, "Edit Surat Sukses", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<FormDisposisi> call, Throwable t) {
                        Toast.makeText(DetailSurat.this, "Mohon Cek Internet", Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
            }
        });
    }
}
