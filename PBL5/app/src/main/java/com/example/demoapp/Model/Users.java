package com.example.demoapp.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Users {
    public int id;
    public String phone;
    public String name;
    public String uid;
    public String url;
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("phone", phone);
        result.put("name", name);
        result.put("uid", uid);
        result.put("url", url);

        return result;
    }
}
