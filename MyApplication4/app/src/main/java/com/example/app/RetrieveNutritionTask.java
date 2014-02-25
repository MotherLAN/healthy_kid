package com.example.app;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class RetrieveNutritionTask extends AsyncTask<String, Void, StringWriter> {
	private Exception exception;
	NutritionAPIRequester nut;
    ArrayList<String> nutrVals;

	public RetrieveNutritionTask(NutritionAPIRequester n) {
		super();
		nut = n;
        nutrVals = new ArrayList<String>();
	}

	protected StringWriter doInBackground(String... urls) {
        DataInputStream input = null;
		try {
			URL url = new URL(urls[0]);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			input = new DataInputStream(connection.getInputStream());
		} catch (Exception e) {
			this.exception = e;
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

		for (JsonNode j : dataset)
			for (String n : NutritionAPIRequester.NUTRIENTS)
				nutrVals.add(j.path(n).toString());
		nut.setVals(nutrVals);
	}
}