package com.user.app.model;

/**
 * Created by User on 3/21/2018.
 */

public class JournalModel {
    private String title;
    private byte[] image;
    private int id;
    private String body;
    private String date;
    private String time;
    private String mood;



    public JournalModel(int id, String title, String body, String mood, byte[] image, String date, String time) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.mood = mood;
        this.image = image;
        this.date = date;
        this.time=time;
    }

    public JournalModel(String title, String body, String mood, byte[] image, String date, String time) {
        this.title = title;
        this.body = body;
        this.mood = mood;
        this.image = image;
        this.date = date;
        this.time=time;
    }


    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
