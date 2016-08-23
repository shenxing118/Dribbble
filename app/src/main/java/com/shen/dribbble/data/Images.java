package com.shen.dribbble.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images implements Parcelable {

    @SerializedName("hidpi")
    @Expose
    private String hidpi;
    @SerializedName("normal")
    @Expose
    private String normal;
    @SerializedName("teaser")
    @Expose
    private String teaser;

    /**
     * @return The hidpi
     */
    public String getHidpi() {
        return hidpi;
    }

    /**
     * @param hidpi The hidpi
     */
    public void setHidpi(String hidpi) {
        this.hidpi = hidpi;
    }

    /**
     * @return The normal
     */
    public String getNormal() {
        return normal;
    }

    /**
     * @param normal The normal
     */
    public void setNormal(String normal) {
        this.normal = normal;
    }

    /**
     * @return The teaser
     */
    public String getTeaser() {
        return teaser;
    }

    /**
     * @param teaser The teaser
     */
    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hidpi);
        dest.writeString(this.normal);
        dest.writeString(this.teaser);
    }

    public Images() {
    }

    Images(Parcel in) {
        this.hidpi = in.readString();
        this.normal = in.readString();
        this.teaser = in.readString();
    }

    public static final Parcelable.Creator<Images> CREATOR = new Parcelable.Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel source) {
            return new Images(source);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };
}