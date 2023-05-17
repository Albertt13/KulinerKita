package com.if41.kulinerkita.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.if41.kulinerkita.API.APIRequestData;
import com.if41.kulinerkita.API.RetroServer;
import com.if41.kulinerkita.Model.ModelResponse;
import com.if41.kulinerkita.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {

    private EditText etNama, etAsal, etDesc;
    private Button btnTambah;
    private String nama, asal, descSingkat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etAsal = findViewById(R.id.et_asal);
        etDesc = findViewById(R.id.et_desc);
        btnTambah = findViewById(R.id.btn_tambah);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                asal = etAsal.getText().toString();
                descSingkat = etDesc.getText().toString();

                if (nama.trim().isEmpty()) {
                    etNama.setError("Nama Tidak Boleh Kosong!!!");
                }
                else if (asal.trim().isEmpty()) {
                    etAsal.setError("Asal Tidak Boleh Kosong!!!");
                }
                else if (descSingkat.trim().isEmpty()) {
                    etDesc.setError("Deskripsi Singkat Tidak Boleh Kosong");
                }
                else {
                tambahKuliner();
                }
            }
        });
    }
    private void tambahKuliner() {
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardCreate(nama,asal,descSingkat);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Kode : " + kode + ", Pesan : " + pesan, Toast.LENGTH_SHORT).show();

                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}