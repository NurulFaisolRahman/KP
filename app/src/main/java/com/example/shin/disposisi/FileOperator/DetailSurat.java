package com.example.shin.disposisi.FileOperator;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.shin.disposisi.FileLogin.ApiClientLogin;
import com.example.shin.disposisi.Respon;
import com.example.shin.disposisi.R;
import com.example.shin.disposisi.Server;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSurat extends AppCompatActivity {

    EditText SuratDari,TanggalSurat,NomorSurat,DiterimaTanggal,NomorAgenda,Perihal;
    TextView JumlahFotoBaru;
    RadioGroup EditSifat;
    RadioButton SangatSegera,Segera,Rahasia,Biasa,Pilihan;
    Button Cetak,Edit,Simpan,UpdateFoto;
    DownloadManager Download;
    ApiEditSurat apiEditSurat;
    ApiDaftarFoto apiDaftarFoto;
    ApiHapusFoto apiHapusFoto;

    private static final int STORAGE_PERMISSION_CODE = 2342;
    private static final int PICK_IMAGE_MULTIPLE = 1;
    List<String> DaftarAlamatURI = new ArrayList<>();
    private static final String UPLOAD_URL = Server.IP+"UploadFoto.php";

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
        JumlahFotoBaru = findViewById(R.id.JumlahFotoBaru);
        UpdateFoto = findViewById(R.id.UpdateFoto);
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
        JumlahFotoBaru.setVisibility(View.GONE);
        UpdateFoto.setVisibility(View.GONE);
        EditSifat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Pilihan = findViewById(EditSifat.getCheckedRadioButtonId());
            }
        });
        apiEditSurat = ApiClientLogin.GetApiClient().create(ApiEditSurat.class);
        apiDaftarFoto = ApiClientLogin.GetApiClient().create(ApiDaftarFoto.class);
        apiHapusFoto = ApiClientLogin.GetApiClient().create(ApiHapusFoto.class);
        requestStoragePermission();

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
                JumlahFotoBaru.setVisibility(View.VISIBLE);
                UpdateFoto.setVisibility(View.VISIBLE);
            }
        });

        UpdateFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TampilkanGaleri();
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
                if (!SuratDari.getText().toString().equals("") && !TanggalSurat.getText().toString().equals("")
                        && !DiterimaTanggal.getText().toString().equals("") && !NomorAgenda.getText().toString().equals("")
                        && !Perihal.getText().toString().equals("") && !(EditSifat.getCheckedRadioButtonId() == -1)){

                    Call<Respon> call = apiEditSurat.EditSurat(NomorSurat.getText().toString(),SuratDari.getText().toString(),TanggalSurat.getText().toString(),DiterimaTanggal.getText().toString(),NomorAgenda.getText().toString(),Pilihan.getText().toString(),Perihal.getText().toString());
                    call.enqueue(new Callback<Respon>() {
                        @Override
                        public void onResponse(Call<Respon> call, Response<Respon> response) {

                        }

                        @Override
                        public void onFailure(Call<Respon> call, Throwable t) {
                            Toast.makeText(DetailSurat.this, "Mohon Cek Internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(DetailSurat.this, "Mohon Lengkapi Inputan", Toast.LENGTH_SHORT).show();
                }
                if (!DaftarAlamatURI.isEmpty()){
                    Call<String[]> call = apiDaftarFoto.DaftarFoto(NomorSurat.getText().toString());
                    call.enqueue(new Callback<String[]>() {
                        @Override
                        public void onResponse(Call<String[]> call, Response<String[]> response) {
                            for (int i = 0; i < response.body().length; i++){
                                Call<String> HapusFoto = apiHapusFoto.HapusFoto(response.body()[i]);
                                HapusFoto.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<String[]> call, Throwable t) {

                        }
                    });
                    for (int i = 0; i < DaftarAlamatURI.size(); i++) {
                        UploadGambar(DaftarAlamatURI.get(i), NomorSurat.getText().toString(), String.valueOf(i+1));
                    }
                }
                Toast.makeText(DetailSurat.this, "Edit Surat Sukses", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void UploadGambar(String Path,String NomorSurat, String id_foto){
        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(DetailSurat.this, uploadId, UPLOAD_URL)
                    .addFileToUpload(Path, "image")
                    .addParameter("nomor_surat", NomorSurat)
                    .addParameter("id_foto", id_foto)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(1)
                    .startUpload(); //Starting the upload
        }
        catch (Exception exc) {
            Toast.makeText(DetailSurat.this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(DetailSurat.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return;
        }
        ActivityCompat.requestPermissions(DetailSurat.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(DetailSurat.this, "Permession granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(DetailSurat.this, "Permession not granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void TampilkanGaleri(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // Cek Pilihan Gambar
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
                // Mendapatkan Gambar
                DaftarAlamatURI = new ArrayList<>();
                if(data.getData() != null){
                    Uri mImageUri = data.getData();
                    DaftarAlamatURI.add(getPath(mImageUri));
                }
                else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            DaftarAlamatURI.add(getPath(uri));
                        }
                    }
                }
                JumlahFotoBaru.setText("  Jumlah Foto = " + DaftarAlamatURI.size() + " Foto");
            } else {
                DaftarAlamatURI = new ArrayList<>();
                JumlahFotoBaru.setText("  Anda Belum Input Foto");
            }
        }
        catch (Exception e) {
            Toast.makeText(DetailSurat.this, "Error", Toast.LENGTH_LONG).show();
        }
    }

    private String getPath(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null,null,null,null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();
        cursor = getContentResolver().query( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,MediaStore.Images.Media._ID+
                " = ? ", new String[]{document_id},null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }
}
