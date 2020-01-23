package com.project.absensi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.project.absensi.R;
import com.project.absensi.model.Login.ModelLogin;


public class ActivityOff extends AppCompatActivity {

    ModelLogin dataModelUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off);

        dataModelUser = getIntent().getParcelableExtra("datauser");

    }

    public void onBackPressed() {
        Intent a = new Intent(ActivityOff.this, Home.class);
        a.putExtra("datauser", (Parcelable) dataModelUser);
        startActivity(a);
        finish();
    }
}
