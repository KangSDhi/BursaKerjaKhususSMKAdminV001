package com.example.sinon.bursakerjakhusussmkadmin.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;
import com.example.sinon.bursakerjakhusussmkadmin.AntarMuka.ItemClickListener;
import com.example.sinon.bursakerjakhusussmkadmin.R;
import com.example.sinon.bursakerjakhusussmkadmin.Umum.Umum;
import de.hdodenhof.circleimageview.CircleImageView;

public class PenggunaKlienViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnCreateContextMenuListener{

    public TextView NamaKlien, AsalSekolahKlien, Jurusan;
    public CircleImageView FotoProfilKlien;

    public ItemClickListener itemClickListener;

    public PenggunaKlienViewHolder(View itemView) {
        super(itemView);

        FotoProfilKlien = itemView.findViewById(R.id.gambarProfilKlien);
        NamaKlien = itemView.findViewById(R.id.namaPengguna);
        AsalSekolahKlien = itemView.findViewById(R.id.namaSekloahAsal);
        Jurusan = itemView.findViewById(R.id.namaJurusan);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.OnClick(v,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Pilih Aksi");

        menu.add(0,0, getAdapterPosition(), Umum.DELETE);
    }
}
