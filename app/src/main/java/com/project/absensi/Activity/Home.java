package com.project.absensi.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.project.absensi.R;
import com.project.absensi.login.LoginTemplate;
import com.project.absensi.model.Login.ModelLogin;
import com.robin.locationgetter.EasyLocation;

public class Home extends AppCompatActivity {

    ImageButton btnMasuk, btnKeluar, btnSakit, btnIzin, btnOff;
    Button btnLogout;
    ModelLogin dataModelUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        dataModelUser = getIntent().getParcelableExtra("datauser");

        btnMasuk = findViewById(R.id.btnMasuk);
        btnKeluar = findViewById(R.id.btnKeluar);
        btnSakit = findViewById(R.id.btnSakit);
        btnIzin = findViewById(R.id.btnIzin);
        btnOff = findViewById(R.id.btnOff);
        btnLogout = findViewById(R.id.btnLogout);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Home.this, ActivityMasuk.class);
                a.putExtra("datauser", (Parcelable) dataModelUser);
                startActivity(a);
            }
        });

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Home.this, ActivityKeluar.class);
                a.putExtra("datauser", (Parcelable) dataModelUser);
                startActivity(a);
            }
        });

        btnSakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Home.this, ActivitySakit.class);
                a.putExtra("datauser", (Parcelable) dataModelUser);
                startActivity(a);
            }
        });

        btnIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Home.this, ActivityIzin.class);
                a.putExtra("datauser", (Parcelable) dataModelUser);
                startActivity(a);
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Home.this, ActivityOff.class);
                a.putExtra("datauser", (Parcelable) dataModelUser);
                startActivity(a);
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setMessage("Yakin Logout ?")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent a = new Intent(Home.this, LoginTemplate.class);
                        startActivity(a);
                        finish();
                    }
                })

                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog =builder.create();
        alertDialog.show();
    }

}
