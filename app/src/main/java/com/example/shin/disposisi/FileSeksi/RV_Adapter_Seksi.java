package com.example.shin.disposisi.FileSeksi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.shin.disposisi.LihatSurat;
import com.example.shin.disposisi.R;
import com.example.shin.disposisi.Surat;
import com.example.shin.disposisi.Respon;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RV_Adapter_Seksi extends RecyclerView.Adapter<RV_Adapter_Seksi.ViewHolderSeksi>{

    private Context ContextSeksi;
    private List<Surat> DataSeksi;
    ApiUpdateSeksi apiUpdateSeksi;
    Retrofit retrofit;

    public RV_Adapter_Seksi(Context contextSeksi, List<Surat> dataSeksi) {
        ContextSeksi = contextSeksi;
        DataSeksi = dataSeksi;
    }

    @NonNull
    @Override
    public ViewHolderSeksi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ContextSeksi).inflate(R.layout.daftar_surat_masuk,parent,false);
        final ViewHolderSeksi viewHolderSeksi = new ViewHolderSeksi(view);
        viewHolderSeksi.Seksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = ContextSeksi.getSharedPreferences("login", Context.MODE_PRIVATE);
                String NamaSub = sharedPreferences.getString("Nama","");
                String NamaBidang = "";
                if (NamaSub.equals("spkp") || NamaSub.equals("spip") || NamaSub.equals("spmk")){
                    NamaBidang = "ikp";
                }
                else if (NamaSub.equals("sst") || NamaSub.equals("set") || NamaSub.equals("sds")){
                    NamaBidang = "sdtik";
                }
                else if (NamaSub.equals("skp") || NamaSub.equals("spe") || NamaSub.equals("sijt")){
                    NamaBidang = "aptika";
                }
                else if (NamaSub.equals("spk") || NamaSub.equals("suk")){
                    NamaBidang = "sekretaris";
                }
                retrofit = new Retrofit.Builder()
                        .baseUrl(ApiUpdateSeksi.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                apiUpdateSeksi = retrofit.create(ApiUpdateSeksi.class);
                Call<Respon> call = apiUpdateSeksi.UpdateSeksi(DataSeksi.get(viewHolderSeksi.getAdapterPosition()).getNomor_surat(),NamaBidang,NamaSub);
                call.enqueue(new Callback<Respon>() {
                    @Override
                    public void onResponse(Call<Respon> call, Response<Respon> response) {
                        if (response.body().getRespon().equals("sukses")){
                            LihatSurat.NomorSurat = DataSeksi.get(viewHolderSeksi.getAdapterPosition()).getNomor_surat();
                            Intent Disposisi = new Intent(ContextSeksi, LihatSurat.class);
                            ContextSeksi.startActivity(Disposisi);
                        }
                    }

                    @Override
                    public void onFailure(Call<Respon> call, Throwable t) {
                        Toast.makeText(ContextSeksi, "Mohon Cek Internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return viewHolderSeksi;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSeksi holder, int position) {
        holder.SuratDari.setText("Surat dari : "+DataSeksi.get(position).getSurat_dari());
        holder.NomorSurat.setText("Nomor Surat : "+DataSeksi.get(position).getNomor_surat());
        holder.TanggalSurat.setText("Tanggal Surat : "+DataSeksi.get(position).getTanggal_surat());
        holder.DiterimaTanggal.setText("Diterima Tanggal : "+DataSeksi.get(position).getDiterima_tanggal());
        holder.NomorAgenda.setText("Nomor Agenda : "+DataSeksi.get(position).getNomor_agenda());
        holder.Sifat.setText("Sifat : "+DataSeksi.get(position).getSifat());
        holder.Perihal.setText("Perihal : "+DataSeksi.get(position).getPerihal());
    }

    @Override
    public int getItemCount() {
        return DataSeksi.size();
    }

    public static class ViewHolderSeksi extends RecyclerView.ViewHolder {

        TextView SuratDari,NomorSurat,TanggalSurat,DiterimaTanggal,NomorAgenda,Sifat,Perihal;
        LinearLayout Seksi;

        public ViewHolderSeksi(View ItemView){
            super(ItemView);

            Seksi = ItemView.findViewById(R.id.Surat);

            SuratDari = ItemView.findViewById(R.id.SuratDari);
            NomorSurat = ItemView.findViewById(R.id.NomorSurat);
            TanggalSurat = ItemView.findViewById(R.id.TanggalSurat);
            DiterimaTanggal= ItemView.findViewById(R.id.DiterimaTanggal);
            NomorAgenda = ItemView.findViewById(R.id.NomorAgenda);
            Sifat = ItemView.findViewById(R.id.Sifat);
            Perihal = ItemView.findViewById(R.id.Perihal);
        }
    }
}
