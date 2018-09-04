package com.example.sinon.bursakerjakhusussmkadmin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sinon.bursakerjakhusussmkadmin.AntarMuka.ItemClickListener;
import com.example.sinon.bursakerjakhusussmkadmin.Model.Lowongan;
import com.example.sinon.bursakerjakhusussmkadmin.Model.PenggunaAdmin;
import com.example.sinon.bursakerjakhusussmkadmin.Umum.Umum;
import com.example.sinon.bursakerjakhusussmkadmin.Umum.UmumBerlaku;
import com.example.sinon.bursakerjakhusussmkadmin.ViewHolder.LowonganViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import dmax.dialog.SpotsDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DasborAdmin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference lowonganRefrence = firebaseDatabase.getReference("Lowongan");


    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    RecyclerView recyclerViewLowongan;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Lowongan, LowonganViewHolder> adapter;

    EditText EdtIdPerusahaan;
    EditText EdtNamaPerusahaan;
    EditText EdtNamaLowongan;
    EditText EdtWaktuBerlaku;
    EditText EdtPersyaratan;

    TextView TextNamaAdmin;

    Button btnSelect, btnUpload;

    Uri saveUri;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasbor_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DasborAdmin.this, TambahLowongan.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set Nama Admin
        View headerView = navigationView.getHeaderView(0);
        TextNamaAdmin = headerView.findViewById(R.id.txtNamaAdmin);
        TextNamaAdmin.setText(Umum.masihBerlakuPenggunaAdmin.getNama());

        //Load Lowongan
        recyclerViewLowongan = (RecyclerView)findViewById(R.id.recyclerLowonganDashbor);
        recyclerViewLowongan.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewLowongan.setLayoutManager(layoutManager);

        memuatLowongan();
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

    private void showUpdateDialog(final String key, final Lowongan item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DasborAdmin.this);
        alertDialog.setTitle("Perbarui Lowongan");
        alertDialog.setMessage("Silahkan Isi Informasi dengan Lengkap!");

        final LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.add_new_menu_layout, null);

        EdtIdPerusahaan = add_menu_layout.findViewById(R.id.edtIdPerusahaan);
        EdtNamaPerusahaan = add_menu_layout.findViewById(R.id.edtNamaPerusahaan);
        EdtNamaLowongan = add_menu_layout.findViewById(R.id.edtNamaLowongan);
        EdtWaktuBerlaku = add_menu_layout.findViewById(R.id.edtWaktuBerlaku);
        EdtPersyaratan = add_menu_layout.findViewById(R.id.edtPersyratan);

        btnSelect = add_menu_layout.findViewById(R.id.btnSelect);
        btnUpload = add_menu_layout.findViewById(R.id.btnUpload);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gantiGambar(item);
            }
        });

        //
        EdtIdPerusahaan.setText(item.getPerusahaanId());
        EdtNamaPerusahaan.setText(item.getPerusahaan());
        EdtNamaLowongan.setText(item.getNama());
        EdtWaktuBerlaku.setText(item.getBerlaku());
        EdtPersyaratan.setText(item.getPersyaratan());

        alertDialog.setView(add_menu_layout);

        alertDialog.setPositiveButton("IYA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //update
                item.setPerusahaanId(EdtIdPerusahaan.getText().toString());
                item.setPerusahaan(EdtNamaPerusahaan.getText().toString());
                item.setNama(EdtNamaLowongan.getText().toString());
                item.setBerlaku(EdtWaktuBerlaku.getText().toString());
                item.setPersyaratan(EdtPersyaratan.getText().toString());
                lowonganRefrence.child(key).setValue(item);
            }
        });

        alertDialog.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }

    private void gantiGambar(final Lowongan item) {
        if (saveUri != null)
        {
            final ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Mengunggah...");
            mDialog.show();

            String imageName = UUID.randomUUID().toString();
            final StorageReference imageFolder = storageReference.child("images/"+imageName);
            imageFolder.putFile(saveUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            Toast.makeText(DasborAdmin.this, "Terunggah", Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    item.setLogoPerusahaan(uri.toString());
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
                            Toast.makeText(DasborAdmin.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            saveUri = data.getData();
            btnSelect.setText("Gambar Dipilih!");
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void pilihGambar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    private void showDeleteDialog(String key) {
        lowonganRefrence.child(key).removeValue();
        Toast.makeText(this, "Lowongan Dihapus", Toast.LENGTH_SHORT).show();
    }

    private void memuatLowongan() {

        adapter = new FirebaseRecyclerAdapter<Lowongan, LowonganViewHolder>(
                Lowongan.class,
                R.layout.item_lowongan,
                LowonganViewHolder.class,
                lowonganRefrence
        ) {
            @Override
            protected void populateViewHolder(LowonganViewHolder viewHolder, Lowongan model, int position) {
                viewHolder.LowNama.setText(model.getNama());
                viewHolder.LowPerusahaan.setText(model.getPerusahaan());
                viewHolder.LowBerlaku.setText(model.getBerlaku());

                final Lowongan lowongan = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(DasborAdmin.this, "Pratinjau "+lowongan.getNama(), Toast.LENGTH_SHORT).show();
                        {
                            Intent lowonganDetailPratinjau = new Intent(DasborAdmin.this, LowonganDetailPratinjau.class);
                            lowonganDetailPratinjau.putExtra("LowonganId",adapter.getRef(position).getKey());
                            startActivity(lowonganDetailPratinjau);
                        }
                    }
                });
            }
        };

        recyclerViewLowongan.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dasbor_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perusahaan) {
            startActivity(new Intent(DasborAdmin.this, DaftarPerusahaan.class));
        } else if (id == R.id.nav_pelamar) {
            startActivity(new Intent(DasborAdmin.this, ListPelamarLowongan.class));
        }else if (id == R.id.nav_pengguna){
            startActivity(new Intent(DasborAdmin.this, ListPenggunaKlien.class));
        }else if (id == R.id.nav_akun_admin) {
            gantiSandiDialog();
        } else if (id == R.id.nav_keluar) {
            Intent keluar = new Intent(DasborAdmin.this, MainActivity.class);
            keluar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(keluar);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gantiSandiDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DasborAdmin.this);
        alertDialog.setTitle("Perbarui Sandi");

        LayoutInflater inflater = LayoutInflater.from(this);
        View add_layout_password = inflater.inflate(R.layout.add_layout_password, null);

        final TextInputEditText EdSandiLama = (TextInputEditText)add_layout_password.findViewById(R.id.edtSandiLama);
        final TextInputEditText EdSandiBaru = (TextInputEditText)add_layout_password.findViewById(R.id.edtSandiBaru);
        final TextInputEditText EdKonfirmasiSandi = (TextInputEditText)add_layout_password.findViewById(R.id.edtKonfirmasiSandiBaru);

        alertDialog.setView(add_layout_password);

        alertDialog.setPositiveButton("Perbarui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //
                final android.app.AlertDialog waitingDialog = new SpotsDialog(DasborAdmin.this);
                waitingDialog.show();

                //
                if (EdSandiLama.getText().toString().equals(Umum.masihBerlakuPenggunaAdmin.getSandi()))
                {
                    if (EdSandiBaru.getText().toString().equals(EdKonfirmasiSandi.getText().toString()))
                    {
                        Map<String, Object> perbaruiSandi = new HashMap<>();
                        perbaruiSandi.put("sandi",EdSandiBaru.getText().toString());

                        //
                        DatabaseReference UserAdmin = FirebaseDatabase.getInstance().getReference("PenggunaAdmin");
                        UserAdmin.child(Umum.masihBerlakuPenggunaAdmin.getNamaAdmin())
                                .updateChildren(perbaruiSandi)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        waitingDialog.dismiss();
                                        Toast.makeText(DasborAdmin.this, "Sandi Diperbarui", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DasborAdmin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    else
                    {
                        waitingDialog.dismiss();
                        Toast.makeText(DasborAdmin.this, "Konfirmasi Sandi Tidak Cocok", Toast.LENGTH_SHORT).show();
                    }
                }
                else 
                {
                    waitingDialog.dismiss();
                    Toast.makeText(DasborAdmin.this, "Sandi Lama Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
