package com.example.a1117p.osam.host;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferences = getSharedPreferences("OsamHost", MODE_PRIVATE);
        findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SplashActivity.this, RegisterActivity.class);
                startActivity(i);
                //finish();
            }
        });
        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.login_form).setVisibility(View.VISIBLE);
                ((Button) v).setText("Log in");
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String id = ((EditText) findViewById(R.id.id)).getText().toString();
                        final String passwd = ((EditText) findViewById(R.id.passwd)).getText().toString();
                        if (id.equals("")) {
                            Toast.makeText(SplashActivity.this, "ID를 입력하세요", Toast.LENGTH_LONG).show();
                            return;
                        } else if (passwd.equals("")) {
                            Toast.makeText(SplashActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_LONG).show();
                            return;
                        }
                        final ProgressDialog dialog = new ProgressDialog(SplashActivity.this);
                        dialog.setMessage("로그인중입니다.");

                        dialog.show();
                        final HashMap params = new HashMap<String, String>();

                        params.put("hostUserId", id);
                        params.put("hostUserPassword", passwd);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final String html = RequestHttpURLConnection.request("https://be-light.store/api/auth/hoster/login", params, "POST");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                        JSONParser parser = new JSONParser();
                                        try {
                                            JSONObject object = (JSONObject) parser.parse(html);
                                            Long status = (Long) object.get("status");
                                            if (status == 200) {
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putString("id", id);
                                                editor.putString("passwd", passwd);

                                                //최종 커밋
                                                editor.apply();


                                                Intent i = new Intent(SplashActivity.this, MapActivity.class);
                                                startActivity(i);
                                                finish();
                                            } else {
                                                Toast.makeText(SplashActivity.this, "ID와 PW를 확인하세요.", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                            Toast.makeText(SplashActivity.this, "에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            }
                        }).start();
                    }
                });
            }
        });
            StartThread(getIntent().getBooleanExtra("needLoading", true));

    }


    void StartThread(boolean b) {
        //Intent i = new Intent(SplashActivity.this, MapActivity.class);
        //startActivity(i);
        //finish();
        if (b) {
            String id =preferences.getString("id", null);
            String passwd = preferences.getString("passwd", null);
            if (id == null ||  passwd== null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                View v = findViewById(R.id.btns);
                                v.setVisibility(View.VISIBLE);
                                AlphaAnimation animation = new AlphaAnimation(0, 1);
                                animation.setDuration(1000);
                                v.startAnimation(animation);

                            }
                        });
                    }
                }).start();
            }
            else{
                final ProgressDialog dialog = new ProgressDialog(SplashActivity.this);
                dialog.setMessage("자동 로그인중입니다.");

                dialog.show();
                final HashMap params = new HashMap<String, String>();

                params.put("hostUserId", id);
                params.put("hostUserPassword", passwd);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String html = RequestHttpURLConnection.request("https://be-light.store/api/auth/hoster/login", params, "POST");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                JSONParser parser = new JSONParser();
                                try {
                                    JSONObject object = (JSONObject) parser.parse(html);
                                    Long status = (Long) object.get("status");
                                    if (status == 200) {


                                        Intent i = new Intent(SplashActivity.this, MapActivity.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(SplashActivity.this, "자동로그인에 실패하였습니다.", Toast.LENGTH_LONG).show();
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.remove("id");
                                        editor.remove("passwd");

                                        //최종 커밋
                                        editor.apply();
                                        StartThread(false);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    Toast.makeText(SplashActivity.this, "에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                }).start();
            }
        } else {
            findViewById(R.id.btns).setVisibility(View.VISIBLE);
            findViewById(R.id.login_btn).performClick();

        }
    }
}
