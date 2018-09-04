package com.example.sinon.bursakerjakhusussmkadmin;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import com.example.sinon.bursakerjakhusussmkadmin.Model.Lowongan;
import com.google.firebase.database.*;

public class LowonganDetail extends AppCompatActivity {

    TextInputEditText EdIdPerusahaan;
    TextInputEditText EdNamaPerusahaan;
    TextInputEditText EdNamaLowongan;
    TextInputEditText EdWaktuBerlaku;
    TextInputEditText EdPersyaratan;

    Button TombolPerbaruiLowongan;

    String lowonganId = "";

    Lowongan lowonganMasihBerlaku;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference refrensiLowongan = firebaseDatabase.getReference("Lowongan");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lowongan_detail);

        EdIdPerusahaan = (TextInputEditText)findViewById(R.id.edIdPerusahaan);
        EdNamaPerusahaan = (TextInputEditText)findViewById(R.id.edNamaPerusahaan);
        EdNamaLowongan = (TextInputEditText)findViewById(R.id.edNamaLowongan);
        EdWaktuBerlaku = (TextInputEditText)findViewById(R.id.edWaktuBerlaku);
        EdPersyaratan = (TextInputEditText)findViewById(R.id.edPersyaratan);

        TombolPerbaruiLowongan = (Button)findViewById(R.id.tombolPerbaruiLowongan);

        //dapatkan Intetnt
        if (getIntent() != null){
            lowonganId = getIntent().getStringExtra("LowonganId");
        }
        if (!lowonganId.isEmpty()){
            dapatkanDetailLowongan(lowonganId);
        }
    }

    private void dapatkanDetailLowongan(final String lowonganId) {
        refrensiLowongan.child(lowonganId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              lowonganMasihBerlaku = dataSnapshot.getValue(Lowongan.class);
              EdIdPerusahaan.setText(lowonganMasihBerlaku.getPerusahaanId());
              EdNamaPerusahaan.setText(lowonganMasihBerlaku.getPerusahaan());
              EdNamaLowongan.setText(lowonganMasihBerlaku.getNama());
              EdWaktuBerlaku.setText(lowonganMasihBerlaku.getBerlaku());
              EdPersyaratan.setText(lowonganMasihBerlaku.getPersyaratan());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
