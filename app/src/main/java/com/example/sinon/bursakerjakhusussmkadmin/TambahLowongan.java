package com.example.sinon.bursakerjakhusussmkadmin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.example.sinon.bursakerjakhusussmkadmin.Model.Lowongan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import de.hdodenhof.circleimageview.CircleImageView;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.UUID;

public class TambahLowongan extends AppCompatActivity {

    CircleImageView GambarLogoPerusahaan;
    TextInputEditText EdIdPerusahaan;
    TextInputEditText EdNamaPerusahaan;
    TextInputEditText EdNamaLowongan;
    TextInputEditText EdWaktuBerlaku;
    TextInputEditText EdPersyratan;

    Button TombolTambahLowongan;
    DatabaseReference referenceLowongan = FirebaseDatabase.getInstance().getReference("Lowongan");

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    //Date Picker
    Calendar calendar;
    int hari, bulan, tahun;

    //Image Uri
    CircleImageView LogoPerusahaanDialog;
    private final int PICK_IMAGE_REQUEST = 71;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_lowongan);

        inisialisasiKomponen();
        fungsiAksi();
    }

    private void fungsiAksi() {

        eventDatePicker();

        aksiTambahLowongan();
    }

    private void aksiTambahLowongan() {

        GambarLogoPerusahaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
            }
        });

        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("Mengunggah");

        TombolTambahLowongan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referenceLowongan.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (imageUri == null){
                            Toast.makeText(TambahLowongan.this, "Logo Kosong", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String imageName = UUID.randomUUID().toString();
                            final StorageReference imageFolder = storageReference.child("images/"+imageName);
                            imageFolder.putFile(imageUri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Lowongan lowongan = new Lowongan(
                                                            EdNamaLowongan.getText().toString(),
                                                            EdNamaPerusahaan.getText().toString(),
                                                            EdIdPerusahaan.getText().toString(),
                                                            EdPersyratan.getText().toString(),
                                                            EdWaktuBerlaku.getText().toString(),
                                                            uri.toString()
                                                    );

                                                    referenceLowongan.push().setValue(lowongan);
                                                    Toast.makeText(TambahLowongan.this, "Lowongan Ditambah", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            mDialog.dismiss();
                                            Toast.makeText(TambahLowongan.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    mDialog.show();
                                    mDialog.setMessage("Proses"+progress+"%");
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void imagePicker() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Edit Foto");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_image_layout = inflater.inflate(R.layout.add_image_layout, null);
        alertDialog.setView(add_image_layout);

        Button BtnPilih = add_image_layout.findViewById(R.id.btnPilih);
        LogoPerusahaanDialog = add_image_layout.findViewById(R.id.imgPerusahaanDialog);

        BtnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });

        alertDialog.setPositiveButton("IYA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                GambarLogoPerusahaan.setImageURI(imageUri);
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

    private void pilihGambar() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST)
        {
            imageUri = data.getData();
            LogoPerusahaanDialog.setImageURI(imageUri);
        }
    }

    private void eventDatePicker() {

        calendar = Calendar.getInstance();

        hari = calendar.get(Calendar.DAY_OF_MONTH);
        bulan = calendar.get(Calendar.MONTH);
        tahun = calendar.get(Calendar.YEAR);

        bulan = bulan+1;

        EdWaktuBerlaku.setText(hari+"/"+bulan+"/"+tahun);

        EdWaktuBerlaku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogAction();
            }
        });
    }

    private void DatePickerDialogAction() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(TambahLowongan.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                EdWaktuBerlaku.setText(dayOfMonth+"/"+month+"/"+year);
            }
        },tahun,bulan,hari);
        datePickerDialog.show();
    }

    private void inisialisasiKomponen() {
        GambarLogoPerusahaan = (CircleImageView) findViewById(R.id.gambarLogoPerusahaan);
        EdIdPerusahaan = (TextInputEditText)findViewById(R.id.edIdPerusahaan);
        EdNamaPerusahaan = (TextInputEditText)findViewById(R.id.edNamaPerusahaan);
        EdNamaLowongan = (TextInputEditText)findViewById(R.id.edNamaLowongan);
        EdWaktuBerlaku = (TextInputEditText)findViewById(R.id.edWaktuBerlaku);
        EdPersyratan = (TextInputEditText)findViewById(R.id.edPersyaratan);
        TombolTambahLowongan = (Button)findViewById(R.id.tombolTambahLowongan);
    }
}
