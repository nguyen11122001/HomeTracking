package com.example.demoapp.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class History implements  Serializable{
    public int id;
    public Boolean isAccepted;
    public String name;
    public String time;
    public String url;

    public History() {

    }

}

