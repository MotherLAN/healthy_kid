package com.example.app;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class RetrieveNutritionTask extends AsyncTask<String, Void, StringWriter> {
    private ArrayList<String> nutrVals;

    public RetrieveNutritionTask() {
        super();
        nutrVals = new ArrayList<String>();
    }

    protected StringWriter doInBackground(String... urls) {
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
        return writer;
    }

    protected void onPostExecute(StringWriter writer) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(writer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonNode dataset = node.get("response").get("data");

        // if (connection.getResponseMessage().equals("OK")) {
        //
        // }

        for (JsonNode j : dataset)
            for (String n : NutritionAPIRequester.NUTRIENTS)
                nutrVals.add(j.path(n).toString());
    }

    ArrayList<String> getNutrVals() {
        return nutrVals;
    }
}