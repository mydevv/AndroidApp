package com.example.testowanie.db;

import android.widget.TextView;

//Model danych dla elementu listy
public class MeetingItem {

    private int id;

    private String time;

    private String title;

    private String place;

    private String participants;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }



    public MeetingItem(int id, String title, String time,  String place,  String participants)
    {
        this.time = time;
        this.title = title;
        this.place = place;
        this.participants = participants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
