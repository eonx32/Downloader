package com.example.Downloader.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class File implements Serializable{

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("ext")
    private String ext;

    @SerializedName("parts")
    private int parts;

    public File(long id, String name, String ext, int parts) {
        this.id = id;
        this.name = name;
        this.ext = ext;
        this.parts = parts;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getExt() {
        return ext;
    }

    public int getParts() {
        return parts;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public void setParts(int parts) {
        this.parts = parts;
    }

    @Override
    public String toString() {
        return "File : {" +
                "id : " + id +
                ", name : " + name +
                ", ext : " + ext +
                ", parts : " + parts +
                "}";
    }
}
