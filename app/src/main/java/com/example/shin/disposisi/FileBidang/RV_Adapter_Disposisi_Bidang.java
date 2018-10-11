package com.example.shin.disposisi.FileBidang;

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

public class RV_Adapter_Disposisi_Bidang extends RecyclerView.Adapter<RV_Adapter_Disposisi_Bidang.ViewHolderDisposisiBidang>{

    private Context ContextDisposisiBidang;
    private List<Surat> DataDisposisiBidang;
    public static String NomorSurat;

    public RV_Adapter_Disposisi_Bidang(Context contextDisposisiBidang, List<Surat> dataDisposisiBidang) {
        ContextDisposisiBidang = contextDisposisiBidang;
        DataDisposisiBidang = dataDisposisiBidang;
    }

    @NonNull
    @Override
    public ViewHolderDisposisiBidang onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ContextDisposisiBidang).inflate(R.layout.daftar_surat_masuk_bidang,parent,false);
        final ViewHolderDisposisiBidang viewHolderDisposisiBidang = new ViewHolderDisposisiBidang(view);
        viewHolderDisposisiBidang.DisposisiBidang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NomorSurat = DataDisposisiBidang.get(viewHolderDisposisiBidang.getAdapterPosition()).getNomor_surat();
                Intent DisposisiBidang = new Intent(ContextDisposisiBidang, BidangMendisposisi.class);
                ContextDisposisiBidang.startActivity(DisposisiBidang);
            }
        });
        return viewHolderDisposisiBidang;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDisposisiBidang holder, int position) {
        holder.SuratDari.setText("Surat dari : "+DataDisposisiBidang.get(position).getSurat_dari());
        holder.NomorSurat.setText("Nomor Surat : "+DataDisposisiBidang.get(position).getNomor_surat());
        holder.TanggalSurat.setText("Tanggal Surat : "+DataDisposisiBidang.get(position).getTanggal_surat());
        holder.DiterimaTanggal.setText("Diterima Tanggal : "+DataDisposisiBidang.get(position).getDiterima_tanggal());
        holder.NomorAgenda.setText("Nomor Agenda : "+DataDisposisiBidang.get(position).getNomor_agenda());
        holder.Sifat.setText("Sifat : "+DataDisposisiBidang.get(position).getSifat());
        holder.Perihal.setText("Perihal : "+DataDisposisiBidang.get(position).getPerihal());
        holder.PesanDisposisi.setText("Isi Disposisi : "+DataDisposisiBidang.get(position).getIsi_diposisi());
    }

    @Override
    public int getItemCount() {
        return DataDisposisiBidang.size();
    }

    public static class ViewHolderDisposisiBidang extends RecyclerView.ViewHolder {

        TextView SuratDari,NomorSurat,TanggalSurat,DiterimaTanggal,NomorAgenda,Sifat,Perihal,PesanDisposisi;
        LinearLayout DisposisiBidang;

        public ViewHolderDisposisiBidang(View ItemView){
            super(ItemView);

            DisposisiBidang = ItemView.findViewById(R.id.Surat);

            SuratDari = ItemView.findViewById(R.id.SuratDari);
            NomorSurat = ItemView.findViewById(R.id.NomorSurat);
            TanggalSurat = ItemView.findViewById(R.id.TanggalSurat);
            DiterimaTanggal= ItemView.findViewById(R.id.DiterimaTanggal);
            NomorAgenda = ItemView.findViewById(R.id.NomorAgenda);
            Sifat = ItemView.findViewById(R.id.Sifat);
            Perihal = ItemView.findViewById(R.id.Perihal);
            PesanDisposisi = ItemView.findViewById(R.id.PesanDisposisi);
        }
    }
}
