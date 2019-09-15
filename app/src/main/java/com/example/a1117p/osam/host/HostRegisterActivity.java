package com.example.a1117p.osam.host;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class HostRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_register);

        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText) findViewById(R.id.hostname)).getText().toString();
                String tel = ((EditText) findViewById(R.id.tel)).getText().toString();
                String address = ((EditText) findViewById(R.id.address)).getText().toString();
                String postalCode = ((EditText) findViewById(R.id.postalcode)).getText().toString();
                if (name.equals("")) {
                    Toast.makeText(HostRegisterActivity.this, "호스트명을 입력하세요", Toast.LENGTH_LONG).show();
                    return;
                } else if (tel.equals("")) {
                    Toast.makeText(HostRegisterActivity.this, "전화번호를 입력하세요", Toast.LENGTH_LONG).show();
                    return;
                }else if (address.equals("")) {
                    Toast.makeText(HostRegisterActivity.this, "주소를 입력하세요", Toast.LENGTH_LONG).show();
                    return;
                }else if (postalCode.equals("")) {
                    Toast.makeText(HostRegisterActivity.this, "우편번호를 입력하세요", Toast.LENGTH_LONG).show();
                    return;
                }
                final HashMap params = new HashMap<String, String>();

                params.put("hostName", name);
                params.put("hostTel", tel);
                params.put("hostAddress", address);
                params.put("hostPostalCode", postalCode);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String html = RequestHttpURLConnection.request("http://121.184.10.219/api/host", params,true, "POST");
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                Toast.makeText(HostRegisterActivity.this, html, Toast.LENGTH_LONG).show();
                            }

                        });

                    }
                }).start();
            }
        });
    }
}
