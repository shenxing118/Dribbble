package com.shen.dribbble.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by shen on 2016/8/7.
 */
public class Like implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private Date createdAt;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @SerializedName("user")
    @Expose
    private User user;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeParcelable(this.user, flags);
    }

    public Like() {
    }

    protected Like(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Like> CREATOR = new Creator<Like>() {
        @Override
        public Like createFromParcel(Parcel source) {
            return new Like(source);
        }

        @Override
        public Like[] newArray(int size) {
            return new Like[size];
        }
    };
}
