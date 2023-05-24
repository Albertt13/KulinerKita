package com.if41.kulinerkita.Activity;

import static com.if41.kulinerkita.R.layout.activity_ubah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class UbahActivity extends AppCompatActivity {
    private String yId, yNama, yAsal, yDesc;
    private EditText etNama, etAsal, etDesc;
    private Button btnUbah;
    private String nama, asal, descSingkat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_ubah);

        Intent ambil = getIntent();
        yId = ambil.getStringExtra("xID");
        yNama = ambil.getStringExtra("xNama");
        yAsal = ambil.getStringExtra("xAsal");
        yDesc = ambil.getStringExtra("xDesc");

        etNama = findViewById(R.id.et_nama);
        etAsal = findViewById(R.id.et_asal);
        etDesc = findViewById(R.id.et_desc);
        btnUbah = findViewById(R.id.btn_ubah);

        etNama.setText(yNama);
        etAsal.setText(yAsal);
        etDesc.setText(yDesc);

        btnUbah.setOnClickListener(new View.OnClickListener() {
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
                    ubahKuliner();
                }
            }
        });
    }

    private void ubahKuliner () {
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardUpdate(yId, nama, asal, descSingkat);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "Kode : " + kode + ", Pesan : " + pesan, Toast.LENGTH_SHORT).show();

                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Gagal Menghubungi Server" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}