package com.example.sinon.bursakerjakhusussmkadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.sinon.bursakerjakhusussmkadmin.AntarMuka.ItemClickListener;
import com.example.sinon.bursakerjakhusussmkadmin.Model.PenggunaKlien;
import com.example.sinon.bursakerjakhusussmkadmin.Umum.Umum;
import com.example.sinon.bursakerjakhusussmkadmin.ViewHolder.PenggunaKlienViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ListPenggunaKlien extends AppCompatActivity {

    DatabaseReference penggunaKlienRefrence = FirebaseDatabase.getInstance().getReference("PenggunaKlien");

    RecyclerView recyclerViewPenggunaKlien;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<PenggunaKlien, PenggunaKlienViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pengguna_klien);

        //Memuat Data Pengguna Klien
        recyclerViewPenggunaKlien = (RecyclerView)findViewById(R.id.recyclerPenggunaKlien);
        recyclerViewPenggunaKlien.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewPenggunaKlien.setLayoutManager(layoutManager);

        memuatDataPenggunaKlien();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Umum.DELETE))
            showDeleteDialog(adapter.getRef(item.getOrder()).getKey());
        return super.onContextItemSelected(item);
    }

    private void showDeleteDialog(String key) {
        penggunaKlienRefrence.child(key).removeValue();
        Toast.makeText(this, "Dihapus"+key, Toast.LENGTH_SHORT).show();
    }

    private void memuatDataPenggunaKlien() {

        adapter = new FirebaseRecyclerAdapter<PenggunaKlien, PenggunaKlienViewHolder>(
                PenggunaKlien.class,
                R.layout.item_pengguna_klien,
                PenggunaKlienViewHolder.class,
                penggunaKlienRefrence
        ) {
            @Override
            protected void populateViewHolder(PenggunaKlienViewHolder viewHolder, final PenggunaKlien model, int position) {
                Picasso.with(getBaseContext()).load(model.getFoto_Profil()).into(viewHolder.FotoProfilKlien);
                viewHolder.NamaKlien.setText(model.getNama());
                viewHolder.AsalSekolahKlien.setText(model.getAsal_sekolah());
                viewHolder.Jurusan.setText(model.getJurusan());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(ListPenggunaKlien.this, "ini "+model.getNama(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        recyclerViewPenggunaKlien.setAdapter(adapter);
    }
}