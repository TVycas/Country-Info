package com.tvycas.countyinfo.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tvycas.countyinfo.model.BoundingBox;
import com.tvycas.countyinfo.model.Currency;
import com.tvycas.countyinfo.model.Language;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Class for different Room database converters.
 */
public class Converters {
    @TypeConverter
    public static Currency currencyFromString(String value) {
        Type listType = new TypeToken<Currency>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String currencyFromObject(Currency currency) {
        Gson gson = new Gson();
        return gson.toJson(currency);
    }

    @TypeConverter
    public static ArrayList<Language> langFromString(String value) {
        Type listType = new TypeToken<ArrayList<Language>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String langFromArrayList(ArrayList<Language> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static BoundingBox boundingBoxFromString(String value) {
        Type listType = new TypeToken<BoundingBox>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String boundingBoxFromObject(BoundingBox boundingBox) {
        Gson gson = new Gson();
        return gson.toJson(boundingBox);
    }
}
