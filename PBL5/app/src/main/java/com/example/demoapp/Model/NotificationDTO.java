package com.example.demoapp.Model;

public class NotificationDTO
{
    public String image;
    public boolean isNotifi;
    public boolean isVerified;
    public String name;
    public String time;


    public NotificationDTO() {
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "image='" + image + '\'' +
                ", isNotifi=" + isNotifi +
                ", isVerified=" + isVerified +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
