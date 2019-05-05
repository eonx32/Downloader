package com.example.Downloader.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FilePart implements Serializable{

    @SerializedName("link")
    private String link;

    @SerializedName("id")
    private int id;

    @SerializedName("available")
    private boolean available;

    public FilePart(String link, int id, boolean available) {
        this.link = link;
        this.id = id;
        this.available = available;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "FilePart : {" +
                "link : " + link
                + ", " + "id : " + id
                + ", " + "available : " + available
                + "}";
    }
}
