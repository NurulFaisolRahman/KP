package com.example.shin.disposisi.FileLogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.shin.disposisi.FileBidang.Bidang;
import com.example.shin.disposisi.FileKadis.Kadis;
import com.example.shin.disposisi.FileOperator.Operator;
import com.example.shin.disposisi.FileSeksi.Seksi;
import com.example.shin.disposisi.R;
import com.example.shin.disposisi.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText nama, sandi;
    Button Login;
    String Nama, Sandi;
    public static ApiInterfaceLogin apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        cekSudahLogin();

        nama = findViewById(R.id.nama);
        sandi = findViewById(R.id.sandi);
        Login = findViewById(R.id.Login);
        apiInterface = ApiClientLogin.GetApiClient().create(ApiInterfaceLogin.class);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Nama = nama.getText().toString();
            Sandi = sandi.getText().toString();
            Call<User> call = apiInterface.performUserLogin(Nama,Sandi);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.body().getResponse().equals("sukses")){
                        simpanDataLogin(Nama, Sandi,response.body().getTinkatan());
                        if (response.body().getTinkatan().equals("operator")){
                            Intent operator = new Intent(Login.this, Operator.class);
                            startActivity(operator);
                            finish();
                        }
                        else if (response.body().getTinkatan().equals("kadis")){
                            Intent kadis = new Intent(Login.this, Kadis.class);
                            startActivity(kadis);
                            finish();
                        }
                        else if (response.body().getTinkatan().equals("bidang")){
                            Intent Bidang = new Intent(Login.this, Bidang.class);
                            startActivity(Bidang);
                            finish();
                        }
                        else if (response.body().getTinkatan().equals("seksi")){
                            Intent Seksi = new Intent(Login.this, Seksi.class);
                            startActivity(Seksi);
                            finish();
                        }
                    }
                    else{
                        Toast.makeText(Login.this, "Nama/Sandi Salah ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(Login.this, "Mohon Cek Koneksi Internet", Toast.LENGTH_SHORT).show();
                }
            });
            }
        });
    }

    private void cekSudahLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String level = sharedPreferences.getString("level","");
        if (!level.isEmpty()){
            if (level.equals("operator")){
                Intent operator = new Intent(Login.this, Operator.class);
                startActivity(operator);
                finish();
            }
            else if (level.equals("kadis")){
                Intent kadis = new Intent(Login.this, Kadis.class);
                startActivity(kadis);
                finish();
            }
            else if (level.equals("bidang")){
                Intent Bidang = new Intent(Login.this, Bidang.class);
                startActivity(Bidang);
                finish();
            }
            else if (level.equals("seksi")){
                Intent Seksi = new Intent(Login.this, Seksi.class);
                startActivity(Seksi);
                finish();
            }
        }
    }

    private void simpanDataLogin(String Nama, String Sandi, String level){
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Nama", Nama);
        editor.putString("Sandi", Sandi);
        editor.putString("level", level);
        editor.apply();
    }
}
