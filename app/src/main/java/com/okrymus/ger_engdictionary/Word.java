package com.okrymus.ger_engdictionary;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by panupong_lee on 3/27/17.
 */

public class Word {
    private static final String JSON_GERMAN = "german";
    private static final String JSON_ENGLISH = "english";
    private static final String JSON_PARTOFSPEECH = "POS";
    private String mEnglish;
    private String mGerman;
    private String mPartOfSpeech;

    public Word(String g, String p,String e) {
        mGerman = g;
        mEnglish = e;
        mPartOfSpeech = p;
    }

    public String getPartOfSpeech() {
        return mPartOfSpeech;
    }

    public void setPartOfSpeech(String mPartOfSpeech) {
        this.mPartOfSpeech = mPartOfSpeech;
    }

    public String getEnglish() {
        return mEnglish;
    }

    public void setEnglish(String mEnglish) {
        this.mEnglish = mEnglish;
    }

    public String getGerman() {
        return mGerman;
    }

    public void setGerman(String mGerman) {
        this.mGerman = mGerman;
    }


    public Word(JSONObject json) throws JSONException {
        mGerman = json.getString(JSON_GERMAN);
        mEnglish = json.getString(JSON_ENGLISH);
        mPartOfSpeech = json.getString(JSON_PARTOFSPEECH);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_GERMAN, mGerman);
        json.put(JSON_ENGLISH, mEnglish);
        json.put(JSON_PARTOFSPEECH, mPartOfSpeech);
        return json;
    }
}
