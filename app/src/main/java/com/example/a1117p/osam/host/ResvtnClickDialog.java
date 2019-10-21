package com.example.a1117p.osam.host;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class ResvtnClickDialog extends Dialog {
    Activity context;
    Button openTime, closeTime;

    public ResvtnClickDialog(@NonNull Activity activity) {
        super(activity);
        this.context = activity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        getWindow().setAttributes(layoutParams);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setContentView(R.layout.host_register_dialog);
        openTime = findViewById(R.id.openTime);
        openTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                TimePickerDialog picker = new TimePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {

                                openTime.setText(String.format("%d:%02d", sHour, sMinute));
                            }
                        }, 0, 0, true);
                picker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                picker.show();
            }
        });
        closeTime = findViewById(R.id.closeTime);
        closeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                TimePickerDialog picker = new TimePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {


                                closeTime.setText(String.format("%d:%02d", sHour, sMinute));
                            }
                        }, 0, 0, true);
                picker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                picker.show();
            }
        });

        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "아직 구현 안됨", Toast.LENGTH_LONG).show();
               /* String HostName = ((EditText) findViewById(R.id.hostname)).getText().toString();
                String HostTel = ((EditText) findViewById(R.id.hosttel)).getText().toString();
                String HostAddress = ((EditText) findViewById(R.id.hostaddr)).getText().toString();
                String HostIntro = ((EditText) findViewById(R.id.hostIntro)).getText().toString();



                String HostPostalCode = "0";
                double lat = 0, lon = 0;

                final HashMap<String, String> params = new HashMap<>();

                params.put("hostName", HostName);
                params.put("hostTel", HostTel);
                params.put("hostAddress", HostAddress);
                params.put("hostIntro", HostIntro);
                params.put("hostOpenTime", openTime.getText().toString());
                params.put("hostCloseTime", closeTime.getText().toString());
                params.put("hostPostalCode", HostPostalCode);
                params.put("hostLatitude", lat + "");
                params.put("hostLongitude", lon + "");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String html = RequestHttpURLConnection.request("https://be-light.store/api/hoster/register", params, "POST");
                        context.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                dialog.dismiss();
                                JSONParser parser = new JSONParser();
                                try {
                                    JSONObject object = (JSONObject) parser.parse(html);
                                    Long status = (Long) object.get("status");
                                    if (status == 200) {
                                        Toast.makeText(RegisterActivity.this, "회원가입에 성공하였습니다.", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "회원가입에 실패하였습니다.", Toast.LENGTH_LONG).show();
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    Toast.makeText(RegisterActivity.this, "에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                                }
                            }

                        });

                    }
                }).start();*/
            }
        });
        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
