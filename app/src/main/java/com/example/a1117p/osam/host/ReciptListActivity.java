package com.example.a1117p.osam.host;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class ReciptListActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipt_list);
        listView = findViewById(R.id.ReciptList);
        listView.setAdapter(null);
        final ProgressDialog Pdialog = new ProgressDialog(this);
        Pdialog.setMessage("예약내역을 불러오는 중입니다.");

        Pdialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    final String html = RequestHttpURLConnection.request("https://be-light.store/api/hoster/order/pending?statusCode=0", null, true, "GET");
                    JSONParser jsonParser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) jsonParser.parse(html);
                    final ReciptListAdapter adapter = new ReciptListAdapter(jsonArray, ReciptListActivity.this);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            Pdialog.dismiss();
                            listView.setAdapter(adapter);
                        }

                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            Toast.makeText(ReciptListActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    });
                }

            }
        }).start();
    }
}
