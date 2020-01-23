package com.project.absensi.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.absensi.APIService.APIClient;
import com.project.absensi.APIService.APIInterfacesRest;
import com.project.absensi.Activity.Home;
import com.project.absensi.R;
import com.project.absensi.model.Login.ModelLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSlide2 extends Fragment {

    ModelLogin dataModelUser;

    EditText un, pw;
    Button btnLogin;
    String username, password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.login_layar2, container, false);

        un = rootView.findViewById(R.id.username);
        pw = rootView.findViewById(R.id.password);
        btnLogin = rootView.findViewById(R.id.btnLogin);

        getData();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!un.getText().toString().isEmpty() && !pw.getText().toString().isEmpty()) {
                    boolean isValid = false;
                    if (username.equalsIgnoreCase(un.getText().toString()) && password.equalsIgnoreCase(pw.getText().toString())) {
                        Intent a = new Intent(getActivity(), Home.class);
                        a.putExtra("datauser", (Parcelable) dataModelUser);
                        startActivity(a);

                        Toast.makeText(getActivity(),
                                "login Succes",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(),
                                "akun belum terdaftar",
                                Toast.LENGTH_SHORT).show();
                    }
                } else if (!un.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(),
                            "please fill any empty field",
                            Toast.LENGTH_SHORT).show();
                } else if (!pw.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(),
                            "please fill any empty field",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    public void getData() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelLogin> data = apiInterface.getData( "7741DC5EB51D553EB8DC029F6F1EC66B");

        data.enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {

                dataModelUser = response.body();
                username = dataModelUser.getData().getSMCode();
                password = dataModelUser.getData().getDateOfBirth();

                Toast.makeText(getActivity(), "Koneksi Berhasil", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t) {

                /*List<DataSiswa> model = SQLite.select()
                        .from(DataSiswa.class)
                        .queryList();*/

                Toast.makeText(getActivity(), "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

}
