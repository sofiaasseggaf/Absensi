package com.project.absensi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.project.absensi.R;
import com.project.absensi.model.Login.ModelLogin;

public class ActivityIzin extends AppCompatActivity {

    ModelLogin dataModelUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izin);

        dataModelUser = getIntent().getParcelableExtra("datauser");

    }

    public void onBackPressed() {
        Intent a = new Intent(ActivityIzin.this, Home.class);
        a.putExtra("datauser", (Parcelable) dataModelUser);
        startActivity(a);
        finish();
    }

}
