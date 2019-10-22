package com.example.a1117p.osam.host;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("호스트들을 불러오는 중 입니다.");

        dialog.show();
        listView = findViewById(R.id.hostList);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String html = RequestHttpURLConnection.request("https://be-light.store/api/host", null, true, "GET");
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        dialog.dismiss();
                        JSONParser parser = new JSONParser();
                        try {
                            Object object = parser.parse(html);
                            if (object instanceof JSONArray) {
                                adapter = new ListViewAdapter((JSONArray) object);
                                listView.setAdapter(adapter);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                });

            }
        }).start();
        final DialogInterface.OnDismissListener listener=new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                final ProgressDialog dialog2 = new ProgressDialog(MainActivity.this);
                dialog2.setMessage("호스트들을 불러오는 중 입니다.");

                dialog2.show();
                listView.setAdapter(null);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String html = RequestHttpURLConnection.request("https://be-light.store/api/host", null, true, "GET");
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                dialog2.dismiss();
                                JSONParser parser = new JSONParser();
                                try {
                                    Object object = parser.parse(html);
                                    if (object instanceof JSONArray) {
                                        adapter = new ListViewAdapter((JSONArray) object);
                                        listView.setAdapter(adapter);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    Toast.makeText(MainActivity.this, "에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }

                        });

                    }
                }).start();
            }
        };
        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new HostRegisterDialog(MainActivity.this);
                dialog.setOnDismissListener(listener);
                dialog.show();
            }
        });
        findViewById(R.id.host_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "뭐하는 버튼이지;;", Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.order_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReciptListActivity.class);
                startActivity(intent);
            }
        });
        final DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestHttpURLConnection.cookie = "";
                Toast.makeText(MainActivity.this, "로그아웃되었습니다.", Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, SplashActivity.class);
                i.putExtra("needLoading", false);
                startActivity(i);
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Dialog dialog = new HostRegisterDialog(MainActivity.this, false, (HostListItem) adapter.getItem(position));
                dialog.setOnDismissListener(listener);
                dialog.show();
            }
        });
        OvalProfile();
        ((TextView) findViewById(R.id.name)).setText(RequestHttpURLConnection.name);
        ((TextView) findViewById(R.id.email)).setText(RequestHttpURLConnection.email);
    }
    void OvalProfile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImageView imageView = findViewById(R.id.profile_img);
            imageView.setBackground(new ShapeDrawable(new OvalShape()));
            imageView.setClipToOutline(true);
        }
    }

}
