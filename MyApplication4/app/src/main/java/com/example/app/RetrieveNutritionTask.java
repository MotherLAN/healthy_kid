package com.example.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class RetrieveNutritionTask extends AsyncTask<String, Void, ArrayList<String>> {
    Context c;
    private ArrayList<String> nutrVals;

    public RetrieveNutritionTask(Context c) {
        super();
        this.c = c;
        nutrVals = new ArrayList<String>();
    }

    protected ArrayList<String> doInBackground(String... urls) {
        if (!isConnected()) {
            Log.w("RetrieveNutritionTask.doInBackground()", "Not connected");
            return null;
        }

        DataInputStream input = null;
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            input = new DataInputStream(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }


        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(input, writer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(writer.toString());
            Log.d("writer", writer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (node == null) {
            Log.w("RetrieveNutritionTask.onPostExecute()", "node is null");
            return null;
        }
        JsonNode dataset = node.get("response").get("data");
        Log.d("data", dataset.toString());

        for (JsonNode j : dataset)
            for (String n : NutritionAPIRequester.NUTRIENTS) {
                String s = j.path(n).toString();
                nutrVals.add(s);
                Log.d("adding nutrition-value", n + '/' + s);
            }
        return nutrVals;
    }

    boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()
                || cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
    }
}
