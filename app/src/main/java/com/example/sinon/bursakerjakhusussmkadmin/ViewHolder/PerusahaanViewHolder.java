package com.example.sinon.bursakerjakhusussmkadmin.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sinon.bursakerjakhusussmkadmin.AntarMuka.ItemClickListener;
import com.example.sinon.bursakerjakhusussmkadmin.R;
import com.example.sinon.bursakerjakhusussmkadmin.Umum.Umum;
import org.w3c.dom.Text;

public class PerusahaanViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnCreateContextMenuListener{

    public TextView txtNamaPerusahaan;
    public ImageView imgPerusahaan;

    private ItemClickListener itemClickListener;

    public PerusahaanViewHolder(View itemView) {
        super(itemView);

        txtNamaPerusahaan = (TextView)itemView.findViewById(R.id.namaPerusahaan);
        imgPerusahaan = (ImageView)itemView.findViewById(R.id.gambarPerusahaan);

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

        menu.add(0, 0, getAdapterPosition(), Umum.UPDATE);
        menu.add(0, 1, getAdapterPosition(), Umum.DELETE );
    }
}
