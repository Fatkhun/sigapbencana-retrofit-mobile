package com.example.bahroel.sigapbencana.activity;

import com.example.bahroel.sigapbencana.R;

public enum CategoryType {
    BANJIR("1", "Banjir"),
    KEBAKARAN("2","Kebakaran"),
    TSUNAMI("3", "Tsunami"),
    GUNUNG_MELETUS("4", "Gunung Meletus"),
    GEMPA_BUMI("5", "Gempa Bumi"),
    ANGIN_KENCANG("6", "Angin Kencang");

    private final String mType;
    private final String mTitle;

    CategoryType(String type, String title){
        mType = type;
        mTitle = title;
    }

    public String getType() {
        return mType;
    }
    public String getTitle() {
        return mTitle;
    }
}
