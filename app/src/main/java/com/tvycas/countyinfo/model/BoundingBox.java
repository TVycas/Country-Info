package com.tvycas.countyinfo.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * A POJO for storing the information on the bounding box of a country.
 */
public class BoundingBox {
    @SerializedName(value = "boundingbox")
    private Double[] boundingBox;

    public BoundingBox(Double[] boundingBox) {
        this.boundingBox = boundingBox;
    }

    public Double[] getBoundingBoxArray() {
        return boundingBox;
    }

    public Double getMinLat() {
        return boundingBox[0];
    }

    public Double getMaxLat() {
        return boundingBox[1];
    }

    public Double getMinLng() {
        return boundingBox[2];
    }

    public Double getMaxLng() {
        return boundingBox[3];
    }

    @NonNull
    @Override
    public String toString() {
        return "Min Lat: " + boundingBox[0] + "; Min Lng: " + boundingBox[2] + "; Max Lat: " + boundingBox[1] + "; Max Lng:" + boundingBox[3];
    }
}
