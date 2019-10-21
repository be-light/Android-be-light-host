package com.example.a1117p.osam.host;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class ReciptListAdapter extends BaseAdapter {
    ArrayList<ReciptListItem> recipts = new ArrayList<>();
    Activity context;

    ReciptListAdapter(JSONArray jsonArray, Activity context) {
        for (Object object : jsonArray) {
            recipts.add(new ReciptListItem((JSONObject) object));
        }
        this.context=context;
    }

    @Override
    public int getCount() {
        return recipts.size();
    }

    @Override
    public Object getItem(int position) {
        return recipts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recipt_list_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView name = convertView.findViewById(R.id.name);
        TextView itemCount = convertView.findViewById(R.id.itemCount);
        TextView term = convertView.findViewById(R.id.term);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ReciptListItem listViewItem = recipts.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        name.setText(listViewItem.getUsername());
        StringBuilder termSB = new StringBuilder();
        termSB.append(listViewItem.getDrop_date() + "~" + listViewItem.getPick_date());
        term.setText(termSB);
        itemCount.setText(listViewItem.getItemCount() + "");

        convertView.findViewById(R.id.accept_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog Pdialog = new ProgressDialog(context);
                Pdialog.setMessage("예약을 승인 중입니다.");

                Pdialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            final String html = RequestHttpURLConnection.request("https://be-light.store/api/hoster/order/pending?statusCode=?_method=PUT&accept=1&reciptNumber="+listViewItem.getRecipt_no() , null, true, "GET");

                            JSONParser parser = new JSONParser();
                            final JSONObject object = (JSONObject) parser.parse(html);
                            context.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    Pdialog.dismiss();
                                    Long status = (Long) object.get("status");
                                    if (status == 200) {
                                        Toast.makeText(context, "성공하였습니다.", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(context, "실패하였습니다.", Toast.LENGTH_LONG).show();
                                    }
                                }

                            });

                        } catch (final Exception e) {
                            context.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            });
                        }

                    }
                }).start();
            }
        });
        convertView.findViewById(R.id.decline_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog Pdialog = new ProgressDialog(context);
                Pdialog.setMessage("예약을 거절 중입니다.");

                Pdialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            final String html = RequestHttpURLConnection.request("https://be-light.store/api/hoster/order/pending?statusCode=?_method=PUT&accept=0&reciptNumber="+listViewItem.getRecipt_no() , null, true, "GET");

                            JSONParser parser = new JSONParser();
                            final JSONObject object = (JSONObject) parser.parse(html);
                            context.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    Pdialog.dismiss();
                                    Long status = (Long) object.get("status");
                                    if (status == 200) {
                                        Toast.makeText(context, "성공하였습니다.", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(context, "실패하였습니다.", Toast.LENGTH_LONG).show();
                                    }
                                }

                            });

                        } catch (final Exception e) {
                            context.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            });
                        }

                    }
                }).start();
            }
        });
        OvalProfile(convertView);
        return convertView;
    }

    void OvalProfile(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImageView imageView = view.findViewById(R.id.profile_img);
            imageView.setBackground(new ShapeDrawable(new OvalShape()));
            imageView.setClipToOutline(true);
        }
    }
}


