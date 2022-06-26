package com.example.demoapp.Model;


import com.google.firebase.database.Exclude;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class History implements  Serializable{
    public Boolean isAccepted;
    public String name;
    public String time;
    public String url;

    public History() {

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("isAccepted", isAccepted);
        result.put("name", name);
        result.put("time", time);
        result.put("url", url);

        return result;
    }
}

