package com.project.absensi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.absensi.APIService.APIClient2;
import com.project.absensi.APIService.APIInterfacesRest;
import com.project.absensi.APIService.AppUtil;
import com.project.absensi.R;
import com.project.absensi.model.Absen.PostAbsen;
import com.project.absensi.model.Login.ModelLogin;
import com.robin.locationgetter.EasyLocation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class ActivityKeluar extends AppCompatActivity {

    Button btnTakePicture, btnKirim;
    ImageView imageView;
    EditText txtKeterangan;
    String pathToFile;
    Bitmap foto;

    ModelLogin dataModelUser;
    Double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keluar);

        dataModelUser = getIntent().getParcelableExtra("datauser");

        btnTakePicture = findViewById(R.id.btnTakePicture);
        btnKirim = findViewById(R.id.btnkirim);
        imageView = findViewById(R.id.imageview);
        txtKeterangan = findViewById(R.id.txtketerangan);

        if (Build.VERSION.SDK_INT>=23){
            requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        new EasyLocation(ActivityKeluar.this, new EasyLocation.EasyLocationCallBack() {
            @Override
            public void permissionDenied() {

            }

            @Override
            public void locationSettingFailed() {

            }

            @Override
            public void getLocation(Location location) {

                lat = location.getLatitude();
                lon = location.getLongitude();

                Log.i("lat", String.valueOf(lat));
                Log.i("lon", String.valueOf(lon));
            }
        });

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtKeterangan.getText().toString().length()== 0) {
                    txtKeterangan.setError("keterangan belum di isi !");
                }
                if(imageView.getDrawable()==null) {
                    Toast.makeText(ActivityKeluar.this, "foto blm di input", Toast.LENGTH_SHORT).show();
                }
                if(txtKeterangan.getText().toString().length() != 0 && imageView.getDrawable() != null){
                    sendDataAbsen(foto);
                    onBackPressed();
                }
            }
        });

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;

            photoFile= createPhotoFile();
            if (photoFile!=null){
                pathToFile= photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(ActivityKeluar.this, "com.project.absensi.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    private File createPhotoFile() {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image= null;
        try {
            image= File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e){
            Log.d("mylog", "Except  :" + e.toString());
        }
        return image;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Bitmap bitmap= BitmapFactory.decodeFile(pathToFile);
                imageView.setImageBitmap(bitmap);
                foto = bitmap;
            }
        }
    }

    //send post data with image
    private void sendDataAbsen(Bitmap bitmap) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String now = formatter.format(new Date());

        File foto = createTempFile(bitmap);
        byte[] bImg1 = AppUtil.FiletoByteArray(foto);
        RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"),bImg1);
        MultipartBody.Part fotox = MultipartBody.Part.createFormData("foto", foto.getName() + ".jpg", requestFile1);

        APIInterfacesRest apiInterface = APIClient2.getClient().create(APIInterfacesRest.class);
        Call<PostAbsen> postAdd = apiInterface.sendDataAbsen(

                toRequestBody(AppUtil.replaceNull(dataModelUser.getData().getSMCode())),
                toRequestBody(AppUtil.replaceNull(dataModelUser.getData().getName())),
                toRequestBody(AppUtil.replaceNull(dataModelUser.getData().getUnit())),
                toRequestBody(AppUtil.replaceNull(dataModelUser.getData().getBranch())),
                toRequestBody(AppUtil.replaceNull(dataModelUser.getData().getPosition())),
                fotox,
                toRequestBody(AppUtil.replaceNull(now)),
                toRequestBody(AppUtil.replaceNull("Keluar")),
                toRequestBody(AppUtil.replaceNull(lat+","+lon)),
                toRequestBody(AppUtil.replaceNull(" - ")),
                toRequestBody(AppUtil.replaceNull(" - ")),
                toRequestBody(AppUtil.replaceNull(" - ")),
                toRequestBody(AppUtil.replaceNull("0000-00-00 00:00:00")),
                toRequestBody(AppUtil.replaceNull(now)),
                toRequestBody(AppUtil.replaceNull(txtKeterangan.getText().toString()))
        );

        postAdd.enqueue(new Callback<PostAbsen>() {
            @Override
            public void onResponse(Call<PostAbsen> call, Response<PostAbsen> response) {

                PostAbsen responServer = response.body();

                if (responServer != null) {
                    Toast.makeText(ActivityKeluar.this,responServer.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<PostAbsen> call, Throwable t) {

                Toast.makeText(ActivityKeluar.this, "Maaf koneksi bermasalah", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });

    }

    //change string to requestbody
    public RequestBody toRequestBody(String value) {
        if (value == null) {
            value = "";
        }
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    private File createTempFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() + "");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public void onBackPressed() {
        Intent a = new Intent(ActivityKeluar.this, Home.class);
        a.putExtra("datauser", (Parcelable) dataModelUser);
        startActivity(a);
        finish();
    }

}
