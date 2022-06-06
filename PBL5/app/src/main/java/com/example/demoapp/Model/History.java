package com.example.demoapp.Model;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class History {
    @SerializedName("name")
    private String name;
    @SerializedName("imgUrl")
    private String imgUrl;
    @SerializedName("timeIn")
    private String timeIn;
}

