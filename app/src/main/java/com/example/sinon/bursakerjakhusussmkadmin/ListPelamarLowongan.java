package com.example.sinon.bursakerjakhusussmkadmin;

import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.sinon.bursakerjakhusussmkadmin.Model.Pelamar;
import com.example.sinon.bursakerjakhusussmkadmin.Umum.Umum;
import com.example.sinon.bursakerjakhusussmkadmin.ViewHolder.LowonganViewHolder;
import com.example.sinon.bursakerjakhusussmkadmin.ViewHolder.PelamarViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListPelamarLowongan extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference listLowonganRefrence = database.getReference("PermintaanLowongan");

    RecyclerView recyclerViewListPelamar;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Pelamar, PelamarViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pelamar_lowongan);

        recyclerViewListPelamar = (RecyclerView)findViewById(R.id.recyclerPelamar);
        recyclerViewListPelamar.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewListPelamar.setLayoutManager(layoutManager);

        memuatPelamar();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Umum.UPDATE_STATUS)) {
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(), adapter.getItem(item.getOrder()));
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(final String key, final Pelamar item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListPelamarLowongan.this);
        alertDialog.setTitle("Perbarui Status");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_status_layout_dialog = inflater.inflate(R.layout.add_status_layout_dialog, null);

        final Spinner SpinnerStatus = add_status_layout_dialog.findViewById(R.id.spinnerStatus);
        final TextInputEditText EditTextPesan = add_status_layout_dialog.findViewById(R.id.editTextPesan);
        EditTextPesan.setEnabled(false);

        //Spinner item text
        ArrayAdapter spinnerAdap = (ArrayAdapter)SpinnerStatus.getAdapter();
        int posisiSpinner = spinnerAdap.getPosition(item.getStatus());
        SpinnerStatus.setSelection(posisiSpinner);
        //
        EditTextPesan.setText(item.getPesan());

        //
        SpinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Belum Di Proses"))
                {
                    EditTextPesan.setEnabled(false);
                    EditTextPesan.setText(item.getPesan());
                }
                else
                {
                    EditTextPesan.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //
        alertDialog.setView(add_status_layout_dialog);

        alertDialog.setPositiveButton("IYA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //Perbarui
                item.setStatus(SpinnerStatus.getSelectedItem().toString());
                item.setPesan(EditTextPesan.getText().toString());
                listLowonganRefrence.child(key).setValue(item);
            }
        });

        alertDialog.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void memuatPelamar() {

        adapter = new FirebaseRecyclerAdapter<Pelamar, PelamarViewHolder>(
                Pelamar.class,
                R.layout.item_list_pelamar,
                PelamarViewHolder.class,
                listLowonganRefrence
        ) {
            @Override
            protected void populateViewHolder(PelamarViewHolder viewHolder, Pelamar model, int position) {
                viewHolder.txtNamaLowongan.setText(model.getNama_lowongan());
                viewHolder.txtNamaPelamar.setText(model.getNama());
                viewHolder.txtNamaPerusahaan.setText(model.getPerusahaan());
                viewHolder.txtStatus.setText(model.getStatus());

            }
        };

        recyclerViewListPelamar.setAdapter(adapter);
    }
}