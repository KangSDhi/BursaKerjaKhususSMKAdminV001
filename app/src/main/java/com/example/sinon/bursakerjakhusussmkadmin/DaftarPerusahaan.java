package com.example.sinon.bursakerjakhusussmkadmin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.sinon.bursakerjakhusussmkadmin.AntarMuka.ItemClickListener;
import com.example.sinon.bursakerjakhusussmkadmin.Model.Perusahaan;
import com.example.sinon.bursakerjakhusussmkadmin.Umum.Umum;
import com.example.sinon.bursakerjakhusussmkadmin.ViewHolder.PerusahaanViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class DaftarPerusahaan extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference perusahaanRefrence;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    RecyclerView recyclerViewPerusahaan;
    RecyclerView.LayoutManager layoutManager;

    EditText EdtIDPerusahaan,EdtNamaPerusahaan;
    Button BtnSelect, BtnUpload;

    FloatingActionButton Fab;

    Perusahaan perusahaanBaru;

    Uri saveUri;
    private final int PICK_IMAGE_REQUEST = 71;

    FirebaseRecyclerAdapter<Perusahaan, PerusahaanViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_perusahaan);

        database = FirebaseDatabase.getInstance();
        perusahaanRefrence = database.getReference("Perusahaan");

        Fab = (FloatingActionButton)findViewById(R.id.fabTambahPerusahaan);
        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahPerusahaanDialog();
            }
        });

        recyclerViewPerusahaan = (RecyclerView)findViewById(R.id.recyclerPerusahaan);
        recyclerViewPerusahaan.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewPerusahaan.setLayoutManager(layoutManager);

        memuatPerusahaan();
    }

    private void tambahPerusahaanDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DaftarPerusahaan.this);
        alertDialog.setTitle("Tambah Perusahaan");

        final LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.add_menu_layout, null);

        EdtIDPerusahaan = add_menu_layout.findViewById(R.id.edtIDPerusahan);
        EdtNamaPerusahaan = add_menu_layout.findViewById(R.id.edtNamaPerusahaan);
        BtnSelect = add_menu_layout.findViewById(R.id.btnSelect);
        BtnUpload = add_menu_layout.findViewById(R.id.btnUpload);

        BtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });

        BtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        alertDialog.setView(add_menu_layout);

        alertDialog.setPositiveButton("IYA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if (perusahaanBaru != null)
                {
                    perusahaanRefrence.child(EdtIDPerusahaan.getText().toString()).setValue(perusahaanBaru);
                    Toast.makeText(DaftarPerusahaan.this, "Perusahaan Baru "+perusahaanBaru.getNama(), Toast.LENGTH_SHORT).show();
                }
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

    private void uploadImage() {
        if (saveUri != null)
        {
            final ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Mengunggah...");
            mDialog.show();

            String imageName = UUID.randomUUID().toString();
            final StorageReference imageFolder = storageReference.child("gambarPerusahaan/"+imageName);
            imageFolder.putFile(saveUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            Toast.makeText(DaftarPerusahaan.this, "Terunggah", Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    perusahaanBaru = new Perusahaan(EdtNamaPerusahaan.getText().toString(), uri.toString());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(DaftarPerusahaan.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mDialog.setMessage("Unggah "+progress+" %");
                        }
                    });
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getTitle().equals(Umum.UPDATE))
        {
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(), adapter.getItem(item.getOrder()));
        }
        else if (item.getTitle().equals(Umum.DELETE))
        {
            showDeleteDialog(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    private void showDeleteDialog(String key) {
        perusahaanRefrence.child(key).removeValue();
        Toast.makeText(this, "Perusahaan Dihapus", Toast.LENGTH_SHORT).show();
    }

    private void showUpdateDialog(final String key, final Perusahaan item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DaftarPerusahaan.this);
        alertDialog.setTitle("Perbarui Perusahaan");

        final LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.add_menu_layout, null);

        EdtNamaPerusahaan = add_menu_layout.findViewById(R.id.edtNamaPerusahaan);
        BtnSelect = add_menu_layout.findViewById(R.id.btnSelect);
        BtnUpload = add_menu_layout.findViewById(R.id.btnUpload);

        EdtNamaPerusahaan.setText(item.getNama());

        BtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });

        BtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gantiGambar(item);
            }
        });

        alertDialog.setView(add_menu_layout);

        alertDialog.setPositiveButton("IYA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setNama(EdtNamaPerusahaan.getText().toString());
                perusahaanRefrence.child(key).setValue(item);
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

    private void gantiGambar(final Perusahaan item) {
        if (saveUri != null)
        {
            final ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Mengunggah...");
            mDialog.show();

            String imageName = UUID.randomUUID().toString();
            final StorageReference imageFolder = storageReference.child("gambarPerusahaan/"+imageName);
            imageFolder.putFile(saveUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            Toast.makeText(DaftarPerusahaan.this, "Terunggah", Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    item.setGambar(uri.toString());
                                }
                            });
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mDialog.setMessage("Unggah "+progress+" %");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(DaftarPerusahaan.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            saveUri = data.getData();
            BtnSelect.setText("Gambar Dipilih!");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void pilihGambar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    private void memuatPerusahaan() {
        adapter = new FirebaseRecyclerAdapter<Perusahaan, PerusahaanViewHolder>(Perusahaan.class, R.layout.item_perusahaan, PerusahaanViewHolder.class, perusahaanRefrence) {
            @Override
            protected void populateViewHolder(PerusahaanViewHolder viewHolder, Perusahaan model, int position) {
                Picasso.with(getBaseContext()).load(model.getGambar()).into(viewHolder.imgPerusahaan);
                viewHolder.txtNamaPerusahaan.setText(model.getNama());

                final Perusahaan klikItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(DaftarPerusahaan.this, ""+klikItem.getNama(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        recyclerViewPerusahaan.setAdapter(adapter);
    }
}