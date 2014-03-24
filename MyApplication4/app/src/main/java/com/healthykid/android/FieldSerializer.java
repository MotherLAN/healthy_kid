package com.healthykid.android;

import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Serializes any objects that aren't boxed primitives using JSON
 */
public final class FieldSerializer {
    private FieldSerializer() {
    }

    public static <T> void saveObject(T t, String name, SharedPreferences.Editor editor) {
        editor.clear();
        try {
            editor.putString(name, new ObjectMapper().writeValueAsString(t));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public static <T> T loadObject(String name, SharedPreferences preferences, Class<T> tClass) {
        T t = null;
        try {
            String s = preferences.getString(name, null);
            if (s != null) {
                Log.d("FieldSerializer", s);
                t = new ObjectMapper().readValue(s, tClass);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }
}
