package com.example.a1117p.osam.host;

import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReciptListItem {
    private String username, drop_date, pick_date;
    private long recipt_no, itemCount;

    ReciptListItem(JSONObject object) {
        username = String.valueOf(object.get("username"));
        drop_date = (String) object.get("drop_date");
        pick_date = (String) object.get("pick_date");
        recipt_no = (Long) object.get("recipt_no");
        itemCount = (Long) object.get("itemCount");
        try {
            SimpleDateFormat from, to;
            from = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            to = new SimpleDateFormat("yy/MM/dd");
            Date date = from.parse(drop_date);
            drop_date = to.format(date);
            date = from.parse(pick_date);
            pick_date = to.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public long getRecipt_no() {
        return recipt_no;
    }

    public long getItemCount() {
        return itemCount;
    }

    public String getUsername() {
        return username;
    }

    public String getDrop_date() {
        return drop_date;
    }

    public String getPick_date() {
        return pick_date;
    }
}
