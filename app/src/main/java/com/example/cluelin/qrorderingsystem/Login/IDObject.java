package com.example.cluelin.qrorderingsystem.Login;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cluelin on 2016-05-08.
 */
public class IDObject implements Parcelable{
    String id;
    String pw;
    String name;
    String birth;
    String phonenum;

    public IDObject(String _id, String _pw, String _name, String _birth, String _phonenum){

        id = _id;
        pw = _pw;
        name = _name;
        birth = _birth;
        phonenum = _phonenum;
    }

    public IDObject(Parcel source){
        id = source.readString();
        pw = source.readString();
        name = source.readString();
        birth = source.readString();
        phonenum = source.readString();
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    public String getPhonenum() {
        return phonenum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
        dest.writeString(getPw());
        dest.writeString(getName());
        dest.writeString(getBirth());
        dest.writeString(getPhonenum());

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public IDObject createFromParcel(Parcel source) {
            return new IDObject(source);
        }

        @Override
        public IDObject[] newArray(int size) {
            return new IDObject[size];
        }
    };
}
