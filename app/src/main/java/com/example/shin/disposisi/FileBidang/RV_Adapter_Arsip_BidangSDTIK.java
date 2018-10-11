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

import java.util.List;

public class RV_Adapter_Arsip_BidangSDTIK extends RecyclerView.Adapter<RV_Adapter_Arsip_BidangSDTIK.ViewHolderArsipBidang>{

    private Context ContextArsipBidang;
    private List<DataArsipBidangSDTIK> DataArsipBidang;
    public static String SST,SET,SDS;

    public RV_Adapter_Arsip_BidangSDTIK(Context contextArsipBidang, List<DataArsipBidangSDTIK> dataArsipBidang) {
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
                SST = DataArsipBidang.get(viewHolderArsipBidang.getAdapterPosition()).getSst();
                SET = DataArsipBidang.get(viewHolderArsipBidang.getAdapterPosition()).getSet();
                SDS = DataArsipBidang.get(viewHolderArsipBidang.getAdapterPosition()).getSds();
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
