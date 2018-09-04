package com.example.sinon.bursakerjakhusussmkadmin.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;
import com.example.sinon.bursakerjakhusussmkadmin.AntarMuka.ItemClickListener;
import com.example.sinon.bursakerjakhusussmkadmin.R;
import com.example.sinon.bursakerjakhusussmkadmin.Umum.Umum;

public class PelamarViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnCreateContextMenuListener{

    public TextView txtNamaLowongan;
    public TextView txtNamaPelamar;
    public TextView txtNamaPerusahaan;
    public TextView txtStatus;

    public ItemClickListener itemClickListener;

    public PelamarViewHolder(View itemView) {
        super(itemView);

        txtNamaLowongan = (TextView)itemView.findViewById(R.id.namaLowongan);
        txtNamaPelamar = (TextView)itemView.findViewById(R.id.namaPelamar);
        txtNamaPerusahaan = (TextView)itemView.findViewById(R.id.namaPerusahaan);
        txtStatus = (TextView)itemView.findViewById(R.id.textStatus);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.OnClick(v, getAdapterPosition(), false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Pilih Aksi");

        menu.add(0,0, getAdapterPosition(), Umum.UPDATE_STATUS);
    }
}
