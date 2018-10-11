package com.example.shin.disposisi.FileKadis;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.shin.disposisi.R;
import com.example.shin.disposisi.Surat;

import java.util.List;

public class RV_Adapter_Arsip_Kadis extends RecyclerView.Adapter<RV_Adapter_Arsip_Kadis.ViewHolderArsipKadis> {

    private Context ContextArsipKadis;
    private List<Surat> DataArsipKadis;
    public static String NomorSurat,BidangTujuan,IsiDisposisi;

    public RV_Adapter_Arsip_Kadis(Context contextArsipKadis, List<Surat> dataArsipKadis) {
        ContextArsipKadis = contextArsipKadis;
        DataArsipKadis = dataArsipKadis;
    }

    @NonNull
    @Override
    public ViewHolderArsipKadis onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ContextArsipKadis).inflate(R.layout.daftar_arsip_kadis, parent, false);
        final ViewHolderArsipKadis viewHolderArsipKadis = new ViewHolderArsipKadis(view);
        viewHolderArsipKadis.ArsipKadis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NomorSurat = DataArsipKadis.get(viewHolderArsipKadis.getAdapterPosition()).getNomor_surat();
                BidangTujuan = DataArsipKadis.get(viewHolderArsipKadis.getAdapterPosition()).getBidang();
                IsiDisposisi = DataArsipKadis.get(viewHolderArsipKadis.getAdapterPosition()).getIsi_diposisi();
                Intent EditDisposisi = new Intent(ContextArsipKadis, Edit_Disposisi_Kadis.class);
                ContextArsipKadis.startActivity(EditDisposisi);
            }
        });
        return viewHolderArsipKadis;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderArsipKadis holder, int position) {
        holder.SuratDari.setText("Surat dari : " + DataArsipKadis.get(position).getSurat_dari());
        holder.NomorSurat.setText("Nomor Surat : " + DataArsipKadis.get(position).getNomor_surat());
    }

    @Override
    public int getItemCount() {
        return DataArsipKadis.size();
    }

    public static class ViewHolderArsipKadis extends RecyclerView.ViewHolder {

        TextView SuratDari, NomorSurat;
        LinearLayout ArsipKadis;

        public ViewHolderArsipKadis(View ItemView) {
            super(ItemView);
            ArsipKadis = ItemView.findViewById(R.id.ArsipKadis);
            SuratDari = ItemView.findViewById(R.id.ArsipKadisSuratDari);
            NomorSurat = ItemView.findViewById(R.id.ArsipKadisNomorSurat);
        }
    }
}
