package com.example.shin.disposisi.FileOperator;

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
import com.example.shin.disposisi.Surat;

import java.util.List;

public class RV_Adapter_Arsip_Operator extends RecyclerView.Adapter<RV_Adapter_Arsip_Operator.ViewHolderArsipOperator>{

    static String SuratDari, TanggalSurat, NomorSurat, DiterimaTanggal, NomorAgenda, Sifat, Perihal, Status;
    private Context ContextArsipOperator;
    private List<Surat> DataArsipOperator;

    public RV_Adapter_Arsip_Operator(Context contextArsipOperator, List<Surat> dataArsipOperator) {
        ContextArsipOperator = contextArsipOperator;
        DataArsipOperator = dataArsipOperator;
    }

    @NonNull
    @Override
    public ViewHolderArsipOperator onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ContextArsipOperator).inflate(R.layout.daftar_arsip_operator,parent,false);
        final ViewHolderArsipOperator viewHolderArsipOperator = new ViewHolderArsipOperator(view);

        viewHolderArsipOperator.ArsipOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuratDari = DataArsipOperator.get(viewHolderArsipOperator.getAdapterPosition()).getSurat_dari();
                TanggalSurat = DataArsipOperator.get(viewHolderArsipOperator.getAdapterPosition()).getTanggal_surat();
                NomorSurat = DataArsipOperator.get(viewHolderArsipOperator.getAdapterPosition()).getNomor_surat();
                DiterimaTanggal = DataArsipOperator.get(viewHolderArsipOperator.getAdapterPosition()).getDiterima_tanggal();
                NomorAgenda = DataArsipOperator.get(viewHolderArsipOperator.getAdapterPosition()).getNomor_agenda();
                Sifat = DataArsipOperator.get(viewHolderArsipOperator.getAdapterPosition()).getSifat();
                Perihal = DataArsipOperator.get(viewHolderArsipOperator.getAdapterPosition()).getPerihal();
                Status = DataArsipOperator.get(viewHolderArsipOperator.getAdapterPosition()).getStatus();
                Intent DetailSurat = new Intent(ContextArsipOperator, DetailSurat.class);
                ContextArsipOperator.startActivity(DetailSurat);
            }
        });
        return viewHolderArsipOperator;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderArsipOperator holder, int position) {
        holder.SuratDari.setText("Surat dari : "+DataArsipOperator.get(position).getSurat_dari());
        holder.StatusSurat.setText("Status Surat : "+DataArsipOperator.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return DataArsipOperator.size();
    }

    public static class ViewHolderArsipOperator extends RecyclerView.ViewHolder {

        TextView SuratDari,StatusSurat;
        LinearLayout ArsipOperator;

        public ViewHolderArsipOperator(View ItemView){
            super(ItemView);

            ArsipOperator = ItemView.findViewById(R.id.ArsipOperator);

            SuratDari = ItemView.findViewById(R.id.ArsipOperatorSuratDari);
            StatusSurat = ItemView.findViewById(R.id.ArsipOperatorStatus);
        }
    }
}
