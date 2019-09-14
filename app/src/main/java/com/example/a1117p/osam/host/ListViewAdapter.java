package com.example.a1117p.osam.host;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    ArrayList<HostListItem> hosts = new ArrayList<>();
    ListViewAdapter(JSONArray jsonArray){
        for(Object object:jsonArray){
            hosts.add(new HostListItem((JSONObject) object));
        }
    }
    @Override
    public int getCount() {
        return hosts.size();
    }

    @Override
    public Object getItem(int position) {
        return hosts.get(position);
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
            convertView = inflater.inflate(R.layout.hostitem, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView idx = convertView.findViewById(R.id.idx) ;
        TextView name = convertView.findViewById(R.id.name) ;
        TextView tel = convertView.findViewById(R.id.tel) ;
        TextView address = convertView.findViewById(R.id.address) ;
        TextView postalcode = convertView.findViewById(R.id.postalcode) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        HostListItem listViewItem = hosts.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        idx.setText("인덱스 : "+listViewItem.getIdx());
        name.setText("호스트명 : "+listViewItem.getName());
        tel.setText("전화번호 : "+listViewItem.getTel());
        address.setText("주소 : "+listViewItem.getAddress());
        postalcode.setText("우편주소 : "+listViewItem.getPostalCode());

        return convertView;
    }
}
