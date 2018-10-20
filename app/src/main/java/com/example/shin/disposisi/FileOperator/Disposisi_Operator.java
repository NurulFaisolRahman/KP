package com.example.shin.disposisi.FileOperator;

import android.Manifest;
import android.content.ClipData;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.app.Activity.RESULT_OK;

public class Disposisi_Operator extends Fragment implements View.OnClickListener {

    View v;
    Button PilihGambar, kirim;
    EditText nomor_surat,surat_dari,tanggal_surat,diterima_tanggal,nomor_agenda,perihal;
    TextView JumlahFoto;
    RadioGroup radio;
    RadioButton radio_button;
    private static final int STORAGE_PERMISSION_CODE = 2342;
    private static final int PICK_IMAGE_MULTIPLE = 1;
    List<String> DaftarAlamatURI = new ArrayList<>();
    private static final String UPLOAD_URL = Server.IP+"UploadFoto.php";
    public static ApiDisposisiOperator apiInterface;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.disposisi_operator,container,false);
        PilihGambar = v.findViewById(R.id.choose);
        kirim = v.findViewById(R.id.kirim);
        nomor_surat = v.findViewById(R.id.nomor_surat);
        surat_dari = v.findViewById(R.id.surat_dari);
        tanggal_surat = v.findViewById(R.id.tanggal_surat);
        diterima_tanggal = v.findViewById(R.id.diterima_tanggal);
        nomor_agenda = v.findViewById(R.id.nomor_agenda);
        perihal = v.findViewById(R.id.perihal);
        JumlahFoto = v.findViewById(R.id.JumlahFoto);
        radio = v.findViewById(R.id.radio);
        apiInterface = ApiClientLogin.GetApiClient().create(ApiDisposisiOperator.class);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radio_button = v.findViewById(radioGroup.getCheckedRadioButtonId());
            }
        });
        requestStoragePermission();
        PilihGambar.setOnClickListener(this);
        kirim.setOnClickListener(this);
        return v;
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return;
        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permession granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Permession not granted", Toast.LENGTH_LONG).show();
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
                JumlahFoto.setText("  Jumlah Foto = " + DaftarAlamatURI.size() + " Foto");
            } else {
                JumlahFoto.setText("  Anda Belum Input Foto");
            }
        }
        catch (Exception e) {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    private String getPath(Uri uri){
        Cursor cursor = getActivity().getContentResolver().query(uri, null,null,null,null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();
        cursor = getActivity().getContentResolver().query( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,MediaStore.Images.Media._ID+
                " = ? ", new String[]{document_id},null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    private void UploadGambar(String Path,String NomorSurat, String id_foto){
        try {
            String uploadId = UUID.randomUUID().toString();
                new MultipartUploadRequest(getContext(), uploadId, UPLOAD_URL)
                        .addFileToUpload(Path, "image")
                        .addParameter("nomor_surat", NomorSurat)
                        .addParameter("id_foto", id_foto)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(1)
                        .startUpload(); //Starting the upload
        }
        catch (Exception exc) {
            Toast.makeText(getContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void UploadFormDisposisi(){
        String NomorSurat = nomor_surat.getText().toString();
        String SuratDari = surat_dari.getText().toString();
        String TanggalSurat = tanggal_surat.getText().toString();
        String DiterimaTanggal = diterima_tanggal.getText().toString();
        String NomorAgenda = nomor_agenda.getText().toString();
        String Sifat = radio_button.getText().toString();
        String Perihal = perihal.getText().toString();
        Call<Respon> call = apiInterface.DisposisiOperator(NomorSurat,SuratDari,TanggalSurat,DiterimaTanggal,NomorAgenda,Sifat,Perihal);
        call.enqueue(new Callback<Respon>() {
            @Override
            public void onResponse(Call<Respon> call, Response<Respon> response) {
                if (response.body().getRespon().equals("sukses")){
                    Toast.makeText(getContext(), "Disposisi Sukses", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Respon> call, Throwable t) {
                Toast.makeText(getContext(), "Mohon Cek Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == kirim){
            if (!nomor_surat.getText().toString().equals("") && !surat_dari.getText().toString().equals("") &&
                !tanggal_surat.getText().toString().equals("") && !diterima_tanggal.getText().toString().equals("") &&
                !nomor_agenda.getText().toString().equals("") && !perihal.getText().toString().equals("") &&
                !(radio.getCheckedRadioButtonId() == -1) && !DaftarAlamatURI.isEmpty()){

                UploadFormDisposisi();
                for (int i = 0; i < DaftarAlamatURI.size(); i++) {
                    UploadGambar(DaftarAlamatURI.get(i), nomor_surat.getText().toString(), String.valueOf(i+1));
                }
                DaftarAlamatURI.clear();
                nomor_surat.setText("");
                surat_dari.setText("");
                tanggal_surat.setText("");
                diterima_tanggal.setText("");
                nomor_agenda.setText("");
                perihal.setText("");
                JumlahFoto.setText("  Anda Belum Input Foto");
                radio.clearCheck();
            }
            else{
                Toast.makeText(getContext(), "Mohon Lengkapi Inputan", Toast.LENGTH_SHORT).show();
            }
        }
        if (v == PilihGambar) {
            TampilkanGaleri();
        }
    }
}



