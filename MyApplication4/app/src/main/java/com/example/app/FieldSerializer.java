package com.example.app;

import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Serializes any objects that aren't boxed primitives using JSON
 */
public final class FieldSerializer {
    private FieldSerializer() {}

    public static <T> void saveObject(T t, String name, SharedPreferences.Editor editor) {
        editor.clear();
        try {
            editor.putString(name, new ObjectMapper().writeValueAsString(t));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public static <T> T loadObject(String name, SharedPreferences preferences) {
        T t = null;
        try {
            t = new ObjectMapper().readValue(preferences.getString(name, null), new TypeReference<T>(){}.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return t;
        }
    }
}
