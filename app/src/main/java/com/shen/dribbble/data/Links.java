package com.shen.dribbble.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shen on 2016/7/25.
 */
public class Links implements Parcelable {
    @SerializedName("web")
    @Expose
    private String web;
    @SerializedName("twitter")
    @Expose
    private String twitter;

    /**
     * @return The web
     */
    public String getWeb() {
        return web;
    }

    /**
     * @param web The web
     */
    public void setWeb(String web) {
        this.web = web;
    }

    /**
     * @return The twitter
     */
    public String getTwitter() {
        return twitter;
    }

    /**
     * @param twitter The twitter
     */
    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.web);
        dest.writeString(this.twitter);
    }

    public Links() {
    }

    protected Links(Parcel in) {
        this.web = in.readString();
        this.twitter = in.readString();
    }

    public static final Parcelable.Creator<Links> CREATOR = new Parcelable.Creator<Links>() {
        @Override
        public Links createFromParcel(Parcel source) {
            return new Links(source);
        }

        @Override
        public Links[] newArray(int size) {
            return new Links[size];
        }
    };
}
