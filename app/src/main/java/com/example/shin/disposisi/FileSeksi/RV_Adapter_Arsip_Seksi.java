package com.example.shin.disposisi.FileSeksi;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.shin.disposisi.LihatSurat;
import com.example.shin.disposisi.R;
import com.example.shin.disposisi.Surat;
import java.util.List;

public class RV_Adapter_Arsip_Seksi extends RecyclerView.Adapter<RV_Adapter_Arsip_Seksi.ViewHolderSeksi>{

    private Context ContextSeksi;
    private List<Surat> DataArsipSeksi;

    public RV_Adapter_Arsip_Seksi(Context contextSeksi, List<Surat> dataSeksi) {
        ContextSeksi = contextSeksi;
        DataArsipSeksi = dataSeksi;
    }

    @NonNull
    @Override
    public ViewHolderSeksi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ContextSeksi).inflate(R.layout.daftar_arsip_operator,parent,false);
        final ViewHolderSeksi viewHolderSeksi = new ViewHolderSeksi(view);
        viewHolderSeksi.Seksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LihatSurat.NomorSurat = DataArsipSeksi.get(viewHolderSeksi.getAdapterPosition()).getNomor_surat();
                Intent Disposisi = new Intent(ContextSeksi, LihatSurat.class);
                ContextSeksi.startActivity(Disposisi);
            }
        });
        return viewHolderSeksi;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSeksi holder, int position) {
        holder.SuratDari.setText("Surat dari : "+DataArsipSeksi.get(position).getSurat_dari());
        holder.NomorSurat.setText("Nomor Surat : "+DataArsipSeksi.get(position).getNomor_surat());
    }

    @Override
    public int getItemCount() {
        return DataArsipSeksi.size();
    }

    public static class ViewHolderSeksi extends RecyclerView.ViewHolder {

        TextView SuratDari,NomorSurat;
        LinearLayout Seksi;

        public ViewHolderSeksi(View ItemView){
            super(ItemView);

            Seksi = ItemView.findViewById(R.id.ArsipOperator);

            SuratDari = ItemView.findViewById(R.id.ArsipOperatorSuratDari);
            NomorSurat = ItemView.findViewById(R.id.ArsipOperatorStatus);
        }
    }
}
