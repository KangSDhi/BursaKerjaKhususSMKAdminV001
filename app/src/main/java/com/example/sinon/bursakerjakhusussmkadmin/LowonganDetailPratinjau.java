package com.example.sinon.bursakerjakhusussmkadmin;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sinon.bursakerjakhusussmkadmin.Model.Lowongan;
import com.google.firebase.database.*;
import com.squareup.picasso.Picasso;

public class LowonganDetailPratinjau extends AppCompatActivity {


    TextView NamaLowongan, WaktuLowonganBerlaku;
    ImageView LogoPerusahaan;
    EditText EdPersyaratan;

    CollapsingToolbarLayout collapsingToolbarLayout;

    //FloatingActionButton TombolLayoutPerbarui;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference RefrensiLowongan;

    Lowongan LowonganBerlaku;

    String lowonganId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lowongan_detail_pratinjau);

        inisialisasiKomponen();
        aksiKomponen();
    }

    private void aksiKomponen() {
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        //Dapatkan Intent
        if (getIntent() != null)
        {
            lowonganId = getIntent().getStringExtra("LowonganId");
        }
        if (!lowonganId.isEmpty())
        {
            dapatkanDetailLowongan(lowonganId);
        }


    }

    private void dapatkanDetailLowongan(String lowonganId) {
        RefrensiLowongan.child(lowonganId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LowonganBerlaku = dataSnapshot.getValue(Lowongan.class);
                collapsingToolbarLayout.setTitle(LowonganBerlaku.getPerusahaan());
                Picasso.with(getBaseContext()).load(LowonganBerlaku.getLogoPerusahaan())
                        .into(LogoPerusahaan);
                NamaLowongan.setText(LowonganBerlaku.getNama());
                WaktuLowonganBerlaku.setText(LowonganBerlaku.getBerlaku());
                EdPersyaratan.setText(LowonganBerlaku.getPersyaratan());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inisialisasiKomponen() {
        NamaLowongan = (TextView)findViewById(R.id.namaLowongan);
        WaktuLowonganBerlaku = (TextView)findViewById(R.id.txtWaktuBerlaku);
        LogoPerusahaan = (ImageView)findViewById(R.id.gambarLogoPerusahaan);
        EdPersyaratan = (EditText)findViewById(R.id.edPersyaratan);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);

        //TombolLayoutPerbarui = (FloatingActionButton)findViewById(R.id.tombolLayoutPerbaruiLowongan);

        //init firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        RefrensiLowongan = firebaseDatabase.getReference("Lowongan");

        EdPersyaratan.setEnabled(false);
    }
}
