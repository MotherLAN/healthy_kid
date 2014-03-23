package com.healthykid.android;

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
import java.util.HashMap;

class RetrieveNutritionTask extends AsyncTask<String, Void, HashMap<String, String>> {
    Context context;
    private HashMap<String, String> nutrVals;

    public RetrieveNutritionTask(Context context) {
        super();
        this.context = context;
        nutrVals = new HashMap<String, String>();
    }

    protected HashMap<String, String> doInBackground(String... urls) {
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

        for (JsonNode key : dataset) {
            String brand = key.path("brand").toString();
            String product_name = key.path("product_name").toString();
            nutrVals.put("NAME", brand.substring(1, brand.length() - 1) + ' ' +
                    product_name.substring(1, product_name.length() - 1));
            for (Nutrient nutrient : Nutrient.values()) {
                String request = nutrient.getRequest();
                String s = key.path(request).toString();
                nutrVals.put(nutrient.name(), s);
                Log.d("adding nutrition-value", request + '/' + s);
            }
        }
        return nutrVals;
    }

    boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()
                || cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
    }
}
