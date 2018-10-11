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

public class RV_Adapter_Disposisi_Kadis extends RecyclerView.Adapter<RV_Adapter_Disposisi_Kadis.ViewHolderDisposisiKadis>{

    private Context ContextDisposisiKadis;
    private List<Surat> DataDisposisiKadis;
    public static String NomorSurat;

    public RV_Adapter_Disposisi_Kadis(Context contextDisposisiKadis, List<Surat> dataDisposisiKadis) {
        ContextDisposisiKadis = contextDisposisiKadis;
        DataDisposisiKadis = dataDisposisiKadis;
    }

    @NonNull
    @Override
    public ViewHolderDisposisiKadis onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ContextDisposisiKadis).inflate(R.layout.daftar_surat_masuk,parent,false);
        final ViewHolderDisposisiKadis viewHolderDisposisiKadis = new ViewHolderDisposisiKadis(view);

        viewHolderDisposisiKadis.DisposisiKadis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NomorSurat = DataDisposisiKadis.get(viewHolderDisposisiKadis.getAdapterPosition()).getNomor_surat();
                Intent Disposisi = new Intent(ContextDisposisiKadis, Disposisi_Surat.class);
                ContextDisposisiKadis.startActivity(Disposisi);
            }
        });
        return viewHolderDisposisiKadis;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDisposisiKadis holder, int position) {
        holder.SuratDari.setText("Surat dari : "+DataDisposisiKadis.get(position).getSurat_dari());
        holder.NomorSurat.setText("Nomor Surat : "+DataDisposisiKadis.get(position).getNomor_surat());
        holder.TanggalSurat.setText("Tanggal Surat : "+DataDisposisiKadis.get(position).getTanggal_surat());
        holder.DiterimaTanggal.setText("Diterima Tanggal : "+DataDisposisiKadis.get(position).getDiterima_tanggal());
        holder.NomorAgenda.setText("Nomor Agenda : "+DataDisposisiKadis.get(position).getNomor_agenda());
        holder.Sifat.setText("Sifat : "+DataDisposisiKadis.get(position).getSifat());
        holder.Perihal.setText("Perihal : "+DataDisposisiKadis.get(position).getPerihal());
    }

    @Override
    public int getItemCount() {
        return DataDisposisiKadis.size();
    }

    public static class ViewHolderDisposisiKadis extends RecyclerView.ViewHolder {

        TextView SuratDari,NomorSurat,TanggalSurat,DiterimaTanggal,NomorAgenda,Sifat,Perihal;
        LinearLayout DisposisiKadis;

        public ViewHolderDisposisiKadis(View ItemView){
            super(ItemView);

            DisposisiKadis = ItemView.findViewById(R.id.Surat);

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
