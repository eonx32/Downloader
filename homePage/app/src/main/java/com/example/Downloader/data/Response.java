package com.example.Downloader.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Response implements Serializable {

    @SerializedName("file")
    private ArrayList<String> files;

    public ArrayList<String> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<String> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "{ files = '" + files + "'"
                + " }";
    }
}
