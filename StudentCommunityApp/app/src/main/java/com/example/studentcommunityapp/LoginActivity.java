package com.example.studentcommunityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static final String TAG = "LoginActivity";
    private EditText loginAccountEditText;
    private EditText loginPasswordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getSupportActionBar().hide();

        loginAccountEditText = findViewById(R.id.login_editText_account);
        loginPasswordEditText = findViewById(R.id.login_editText_password);
        final Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginAddress = "http://81.70.27.208:8080/api/login";
                final String loginAccount = loginAccountEditText.getText().toString();
                final String loginPassword = loginPasswordEditText.getText().toString();
                //OKhttp3(loginAddress,loginAccount,loginPassword);
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void OKhttp3(final String address, final String account, final String password){
        Thread a= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HashMap<String,String> json=new HashMap<String, String>();
                    json.put("name",account);
                    json.put("psw",password);
                    Gson gson=new Gson();
                    String r=gson.toJson(json);
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody = RequestBody.create(r,JSON);
                    Request request=new Request.Builder()
                            .url(address)
                            .post(requestBody)
                            .build();
                    Response response=client.newCall(request).execute();
                    final String responseData=response.body().string();
                    if(response.isSuccessful()){
                        Log.e("ResponseData: ",responseData);
                        if(responseData.equals("1")){
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (responseData.equals("-2")) {
                                        Toast.makeText(LoginActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                                    } else if (responseData.equals("-1")) {
                                        Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    Log.e("LoginActivity", "error : ", e);
                }
            }
        });
        a.start();

    }
}
