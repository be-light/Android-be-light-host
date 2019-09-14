package com.example.a1117p.osam.host;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.simple.JSONObject;

public class HostListItem implements Parcelable {
    private String name,tel,address,postalCode,idx;
    HostListItem(JSONObject object){
        idx = String.valueOf(object.get("hostIdx"));
        name = (String)object.get("hostName");
        tel = (String)object.get("hostTel");
        address = (String)object.get("hostAddress");
        postalCode = (String)object.get("hostPostalCode");
    }

    protected HostListItem(Parcel in) {
        name = in.readString();
        tel = in.readString();
        address = in.readString();
        postalCode = in.readString();
        idx = in.readString();
    }

    public static final Creator<HostListItem> CREATOR = new Creator<HostListItem>() {
        @Override
        public HostListItem createFromParcel(Parcel in) {
            return new HostListItem(in);
        }

        @Override
        public HostListItem[] newArray(int size) {
            return new HostListItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getIdx() {
        return idx;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(tel);
        dest.writeString(address);
        dest.writeString(postalCode);
        dest.writeString(idx);
    }
}
