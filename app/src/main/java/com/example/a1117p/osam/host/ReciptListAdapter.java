package com.example.a1117p.osam.host;

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

import java.util.ArrayList;

public class ReciptListAdapter extends BaseAdapter {
    ArrayList<ReciptListItem> reviews = new ArrayList<>();

    ReciptListAdapter(JSONArray jsonArray) {
        for (Object object : jsonArray) {
            reviews.add(new ReciptListItem((JSONObject) object));
        }
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

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
        ReciptListItem listViewItem = reviews.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        name.setText(listViewItem.getUsername());
        StringBuilder termSB = new StringBuilder();
        termSB.append(listViewItem.getDrop_date() + "~" + listViewItem.getPick_date());
        term.setText(termSB);
        itemCount.setText(listViewItem.getItemCount() + "");

        convertView.findViewById(R.id.accept_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "미구현", Toast.LENGTH_LONG).show();
            }
        });
        convertView.findViewById(R.id.decline_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "미구현", Toast.LENGTH_LONG).show();
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


