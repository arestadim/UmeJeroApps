package com.example.myapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.Keranjang;
import com.example.myapplication.model.KeranjangWithRelations;
import com.example.myapplication.model.ProdukWithRelations;

public class KeranjangTambah extends AppCompatActivity {
    AppDatabase mDb;
    EditText mKuantiti;
    Button mSubmit;
    Button bPlus;
    Button bMin;
    TextView mNama;
    int mIdProduk;
    Intent intent;
    ProdukWithRelations produk;
    KeranjangWithRelations keranjangWithRelations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang_tambah);
        mDb = AppDatabase.getDatabase(getApplicationContext());
        intent = getIntent();
        bPlus = findViewById(R.id.btnTambahKuantiti);
        bMin = findViewById(R.id.btnKurangKuantiti);
        mIdProduk = intent.getIntExtra("insert",-1);
        mKuantiti = findViewById(R.id.etKuantitiTambah);
        mNama = findViewById(R.id.labelNamaProdukTambah);
        mSubmit = findViewById(R.id.btnSaveKuantitiTambah);
        mSubmit = findViewById(R.id.btnSaveKuantitiTambah);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });
        bPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int jumlah = Integer.parseInt(String.valueOf(mKuantiti.getText()));
                if(jumlah == 0){
                    jumlah = 1;
                }else {
                    jumlah = jumlah + 1;
                }
                mKuantiti.setText(String.valueOf(jumlah));
            }
        });
        bMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int jumlah = Integer.parseInt(String.valueOf(mKuantiti.getText()));
                if(jumlah == 0){
                    jumlah = 0;
                }else {
                    jumlah = jumlah - 1;
                }
                mKuantiti.setText(String.valueOf(jumlah));
            }
        });

        produk = mDb.produkDao().loadProdukById(mIdProduk);
        mNama.setText(produk.produk.getNama_produk());
    }

    public void onSubmit() {
        final Keranjang data = new Keranjang(
                mIdProduk,
                Integer.parseInt(mKuantiti.getText().toString())
        );

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                keranjangWithRelations = mDb.keranjangDao().loadKeranjangByIdProduk(mIdProduk);
//                Log.e("id_keranjang",""+keranjangWithRelations.keranjang.getId());
                if (keranjangWithRelations != null){
                    final Keranjang data = new Keranjang(
                            mIdProduk,
                            Integer.parseInt(mKuantiti.getText().toString()) + keranjangWithRelations.keranjang.getQty()
                    );
                    data.setId(keranjangWithRelations.keranjang.getId());
                    mDb.keranjangDao().updateKeranjang(data);
                }
                else{
                    mDb.keranjangDao().insertKeranjang(data);
                }
                finish();
            }
        });


    }
}
