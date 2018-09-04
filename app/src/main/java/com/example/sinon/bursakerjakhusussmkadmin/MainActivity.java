package com.example.sinon.bursakerjakhusussmkadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.sinon.bursakerjakhusussmkadmin.Model.PenggunaAdmin;
import com.example.sinon.bursakerjakhusussmkadmin.Umum.Umum;
import com.google.firebase.database.*;

public class MainActivity extends AppCompatActivity {

    TextInputEditText InputTextNamaAdmin;
    TextInputEditText InputTextSandi;
    Button TombolMasuk;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference penggunaAdminReferensi = firebaseDatabase.getReference("PenggunaAdmin");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inisialisasiKomponen();
        kumpulanfungsi();
    }

    private void kumpulanfungsi() {

        InputTextNamaAdmin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(InputTextNamaAdmin.getText().toString())){
                    InputTextSandi.setEnabled(true);
                }else {
                    InputTextSandi.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        InputTextSandi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(InputTextSandi.getText().toString())){
                    TombolMasuk.setEnabled(false);
                }else {
                    TombolMasuk.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        TombolMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penggunaAdminReferensi.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(InputTextNamaAdmin.getText().toString()).exists())
                        {
                            PenggunaAdmin penggunaAdmin = dataSnapshot.child(InputTextNamaAdmin.getText().toString()).getValue(PenggunaAdmin.class);
                            penggunaAdmin.setNamaAdmin(InputTextNamaAdmin.getText().toString()); //ID Admin
                            if (penggunaAdmin.getSandi().equals(InputTextSandi.getText().toString()))
                            {
                                Toast.makeText(MainActivity.this, "Selamat Datang "+penggunaAdmin.getNamaAdmin(), Toast.LENGTH_SHORT).show();
                                {
                                    Umum.masihBerlakuPenggunaAdmin = penggunaAdmin;
                                    startActivity(new Intent(MainActivity.this, DasborAdmin.class));
                                    finish();
                                    penggunaAdminReferensi.removeEventListener(this);
                                }
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Kata Sandi Salah", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Anda Bukan Admin", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    private void inisialisasiKomponen() {
        InputTextNamaAdmin = (TextInputEditText)findViewById(R.id.inputTextNamaAdmin);
        InputTextSandi = (TextInputEditText)findViewById(R.id.inputTextSandi);
        TombolMasuk = (Button)findViewById(R.id.tombolMasuk);

        InputTextSandi.setEnabled(false);
        TombolMasuk.setEnabled(false);
    }
}
