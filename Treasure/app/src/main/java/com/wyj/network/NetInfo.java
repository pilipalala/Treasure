package com.wyj.network;


import android.os.Parcel;
import android.os.Parcelable;


public class NetInfo implements Parcelable {
    private int type;       //网络类型
    private int strength;   //网络强度
    private int level;      //网络级别

    public NetInfo() {

    }


    public NetInfo(int type, int strength, int level) {
        this.type = type;
        this.strength = strength;
        this.level = level;
    }

    @Override
    public String toString() {
        return "NetInfo{" +
                "type=" + type +
                ", Strength=" + strength +
                ", level=" + level +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeInt(this.strength);
        dest.writeInt(this.level);
    }

    protected NetInfo(Parcel in) {
        this.type = in.readInt();
        this.strength = in.readInt();
        this.level = in.readInt();
    }

    public static final Parcelable.Creator<NetInfo> CREATOR = new Parcelable.Creator<NetInfo>() {
        @Override
        public NetInfo createFromParcel(Parcel source) {
            return new NetInfo(source);
        }

        @Override
        public NetInfo[] newArray(int size) {
            return new NetInfo[size];
        }
    };
}
