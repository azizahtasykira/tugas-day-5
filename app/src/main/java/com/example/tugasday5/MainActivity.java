package com.example.tugasday5;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etNama, etKodeBarang1, etKodeBarang2, etKodeBarang3, etJumlahBarang, editText1, editText2, editText3, editText4, editText5;
    RadioGroup radioGroup;
    Button btnProses;
    TextInputLayout textInputLayout1, textInputLayout2, textInputLayout3, textInputLayout4, textInputLayout5;

    private final Map<String, String> daftarBarang = new HashMap<String, String>() {{
        put("SGS", "Samsung Galaxy S");
        put("IPX", "Iphone X");
        put("PCO", "POCO M3");
        put("O17", "Oppo A17");
        put("OAS", "Oppo a5s");
        put("AAE", "Acer Aspire E14");
        put("AV4", "Asus Vivobook 14");
        put("LV3", "Lenovo V14 Gen 3");
        put("AA5", "Acer Aspire 5");
        put("MP3", " Macbook Pro M3");
        // Tambahkan barang lainnya di sini
    }};

    private final Map<String, Double> hargaBarang = new HashMap<String, Double>() {{
        put("SGS", 12999999.0);
        put("IPX", 5725300.0);
        put("PCO", 2730551.0);
        put("O17", 2500999.0);
        put("OAS", 1989123.0);
        put("AAE", 8676981.0);
        put("AV4", 9150999.0);
        put("LV3", 6666666.0);
        put("AA5", 9999999.0);
        put("MP3", 28999999.0);
        // Tambahkan harga barang lainnya di sini
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNama = findViewById(R.id.text1);
        etKodeBarang1 = findViewById(R.id.kode);
        etKodeBarang2 = findViewById(R.id.kode2);
        etKodeBarang3 = findViewById(R.id.kode3);
        etJumlahBarang = findViewById(R.id.text2);
        radioGroup = findViewById(R.id.radioGroup);
        btnProses = findViewById(R.id.button);
        textInputLayout1 = findViewById(R.id.textInputLayoutNama);
        textInputLayout2 = findViewById(R.id.textInputLayoutKode1);
        textInputLayout3 = findViewById(R.id.textInputLayoutKode2);
        textInputLayout4 = findViewById(R.id.textInputLayoutKode3);
        textInputLayout5= findViewById(R.id.textInputLayoutJumlahBarang);

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = etNama.getText().toString();
                String kodeBarang1 = etKodeBarang1.getText().toString().toUpperCase();
                String kodeBarang2 = etKodeBarang2.getText().toString().toUpperCase();
                String kodeBarang3 = etKodeBarang3.getText().toString().toUpperCase();
                String jumlahBarangString = etJumlahBarang.getText().toString();
                EditText editText1 = textInputLayout1.getEditText();
                EditText editText2 = textInputLayout2.getEditText();
                EditText editText3 = textInputLayout3.getEditText();
                EditText editText4 = textInputLayout4.getEditText();
                EditText editText5 = textInputLayout5.getEditText();

                if (TextUtils.isEmpty(jumlahBarangString)) {
                    Toast.makeText(MainActivity.this, "Masukkan jumlah barang", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.isDigitsOnly(jumlahBarangString)) {
                    Toast.makeText(MainActivity.this, "Jumlah barang harus berupa angka", Toast.LENGTH_SHORT).show();
                    return;
                }

                int jumlahBarang = Integer.parseInt(jumlahBarangString);

                if (TextUtils.isEmpty(kodeBarang1) || TextUtils.isEmpty(kodeBarang2) || TextUtils.isEmpty(kodeBarang3)) {
                    Toast.makeText(MainActivity.this, "Masukkan semua kode barang", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!daftarBarang.containsKey(kodeBarang1) || !daftarBarang.containsKey(kodeBarang2) || !daftarBarang.containsKey(kodeBarang3)) {
                    Toast.makeText(MainActivity.this, "Kode barang tidak valid", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Hitung total harga
                double totalHarga = calculateTotalHarga(kodeBarang1, kodeBarang2, kodeBarang3, jumlahBarang);

                // Ambil tipe pelanggan yang dipilih
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioButtonId);
                String tipePelanggan = radioButton.getText().toString();

                // Hitung diskon berdasarkan tipe pelanggan
                double diskon = calculateDiskon(tipePelanggan);

                // Lakukan penanganan diskon khusus jika total harga lebih dari 10 juta
                if (totalHarga > 10000000) {
                    totalHarga -= 100000;
                }

                // Hitung total harga setelah diskon
                double totalHargaSetelahDiskon = totalHarga * (1 - diskon);

                // Hitung jumlah bayar
                double jumlahBayar = totalHargaSetelahDiskon;

                // Lanjutkan ke halaman MainActivity2 dengan mengirimkan data
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("Nama", nama);
                intent.putExtra("TipeMember", tipePelanggan);
                intent.putExtra("KodeBarang1", kodeBarang1);
                intent.putExtra("KodeBarang2", kodeBarang2);
                intent.putExtra("KodeBarang3", kodeBarang3);
                intent.putExtra("JumlahBarang", jumlahBarang);
                intent.putExtra("TotalHarga", totalHarga);
                intent.putExtra("Diskon", diskon);
                intent.putExtra("TotalHargaSetelahDiskon", totalHargaSetelahDiskon);
                intent.putExtra("JumlahBayar", jumlahBayar);

                // Menambahkan deskripsi barang dan harga ke intent
                intent.putExtra("NamaBarang1", daftarBarang.get(kodeBarang1));
                intent.putExtra("NamaBarang2", daftarBarang.get(kodeBarang2));
                intent.putExtra("NamaBarang3", daftarBarang.get(kodeBarang3));
                intent.putExtra("Harga1", hargaBarang.get(kodeBarang1));
                intent.putExtra("Harga2", hargaBarang.get(kodeBarang2));
                intent.putExtra("Harga3", hargaBarang.get(kodeBarang3));

                startActivity(intent);
            }
        });
    }

    private double calculateTotalHarga(String kodeBarang1, String kodeBarang2, String kodeBarang3, int jumlahBarang) {
        double hargaBarang1 = hargaBarang.get(kodeBarang1);
        double hargaBarang2 = hargaBarang.get(kodeBarang2);
        double hargaBarang3 = hargaBarang.get(kodeBarang3);
        return (hargaBarang1 + hargaBarang2 + hargaBarang3) * jumlahBarang;
    }

    private double calculateDiskon(String tipePelanggan) {
        switch (tipePelanggan) {
            case "Gold":
                return 0.1;
            case "Silver":
                return 0.05;
            case "Biasa":
                return 0.02;
            default:
                return 0;
        }
    }
}