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
import android.widget.Toast;

import com.example.shin.disposisi.R;

import java.util.List;

public class RV_Adapter_Arsip_BidangAPTIKA extends RecyclerView.Adapter<RV_Adapter_Arsip_BidangAPTIKA.ViewHolderArsipBidang>{

    private Context ContextArsipBidang;
    private List<DataArsipBidangAPTIKA> DataArsipBidang;
    static String SKP,SPE,SIJT;

    public RV_Adapter_Arsip_BidangAPTIKA(Context contextArsipBidang, List<DataArsipBidangAPTIKA> dataArsipBidang) {
        ContextArsipBidang = contextArsipBidang;
        DataArsipBidang = dataArsipBidang;
    }

    @NonNull
    @Override
    public ViewHolderArsipBidang onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ContextArsipBidang).inflate(R.layout.daftar_arsip_operator,parent,false);
        final ViewHolderArsipBidang viewHolderArsipBidang = new ViewHolderArsipBidang(view);
        viewHolderArsipBidang.ArsipBidang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditDisposisiBidang.NomorSurat = DataArsipBidang.get(viewHolderArsipBidang.getAdapterPosition()).getNomor_surat();
                SKP = DataArsipBidang.get(viewHolderArsipBidang.getAdapterPosition()).getSkp();
                SPE = DataArsipBidang.get(viewHolderArsipBidang.getAdapterPosition()).getSpe();
                SIJT = DataArsipBidang.get(viewHolderArsipBidang.getAdapterPosition()).getSijt();
                Intent EditDisposisi = new Intent(ContextArsipBidang, EditDisposisiBidang.class);
                ContextArsipBidang.startActivity(EditDisposisi);
            }
        });
        return viewHolderArsipBidang;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderArsipBidang holder, int position) {
        holder.SuratDari.setText("Nomor Surat : "+DataArsipBidang.get(position).getNomor_surat());
        holder.StatusSurat.setText("Status Surat : "+DataArsipBidang.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return DataArsipBidang.size();
    }

    public static class ViewHolderArsipBidang extends RecyclerView.ViewHolder {

        TextView SuratDari,StatusSurat;
        LinearLayout ArsipBidang;

        public ViewHolderArsipBidang(View ItemView){
            super(ItemView);

            ArsipBidang = ItemView.findViewById(R.id.ArsipOperator);

            SuratDari = ItemView.findViewById(R.id.ArsipOperatorSuratDari);
            StatusSurat = ItemView.findViewById(R.id.ArsipOperatorStatus);
        }
    }
}
