package com.tvycas.countyinfo.model;

import androidx.annotation.NonNull;

public class Language {
    private String code2;
    private String code3;
    private String name;
    private String nativeName;

    public Language(String code2, String code3, String name, String nativeName) {
        this.code2 = code2;
        this.code3 = code3;
        this.name = name;
        this.nativeName = nativeName;
    }

    public String getCode2() {
        return code2;
    }

    public String getCode3() {
        return code3;
    }

    public String getName() {
        return name;
    }

    public String getNativeName() {
        return nativeName;
    }

    @NonNull
    @Override
    public String toString() {
        return "Code2: " + code2 + "; Code3: " + code3 + "; Name: " + name + "; NativeName: " + nativeName;
    }
}
