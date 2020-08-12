package com.tvycas.countyinfo.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class BoundingBox {
    @SerializedName(value = "boundingbox")
    Double[] boundingBox;

    public BoundingBox(Double[] boundingBox) {
        this.boundingBox = boundingBox;
    }

    public Double[] getBoundingBoxArray() {
        return boundingBox;
    }

    //TODO
    public Double getMinLat() {
        return null;
    }

    @NonNull
    @Override
    public String toString() {
        return "Min Lat: " + boundingBox[0] + "; Min Lng: " + boundingBox[1] + "; Max Lat: " + boundingBox[2] + "; Max Lng:" + boundingBox[3];
    }
}
