package com.example.testfragment.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public class User implements Parcelable {
    private String fullName, username;

    @DrawableRes
    private int photoRes;

    private Post post;

    public User() {

    }

    public User(String fullName, String username, int photoRes, Post post) {
        this.fullName = fullName;
        this.username = username;
        this.photoRes = photoRes;
        this.post = post;
    }

    protected User(Parcel in) {
        fullName = in.readString();
        username = in.readString();
        photoRes = in.readInt();
        post = in.readParcelable(Post.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(fullName);
        parcel.writeString(username);
        parcel.writeInt(photoRes);
        parcel.writeParcelable(post, i);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPhotoRes() {
        return photoRes;
    }

    public void setPhotoRes(int photoRes) {
        this.photoRes = photoRes;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
