package com.example.app;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static java.lang.System.out;

class NutritionAPIRequester {
    private static final String KEY = "v9AM5yVkdkdPUi7OnuzeKAbCideeimaG0nLZFP6t";
    private static final String URL = "http://api.v3.factual.com/t/products-cpg-nutrition";

    public static HashMap<Nutrient, String> getNutrition(String upc, Context context) {
        String query = "";
        String[] vars = new String[]{"q", "KEY"};
        String[] values = new String[]{upc, KEY};
        // 04913207 is the UPC code for a sprite can
        // 04043108 is m&m's

        for (int i = 0; i < vars.length; i++)
            query += '&' + vars[i] + '=' + values[i];
        query = query.substring(1);

        RetrieveNutritionTask r = new RetrieveNutritionTask(context);
        r.execute(URL + "?" + query);

        HashMap<Nutrient, String> nutrVals = null;
        try {
            nutrVals = r.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (nutrVals == null)
            Log.w("NutritionAPIRequester", "Not a food item");
        else
            Log.d("Final nutr-vals", nutrVals.toString());
        return nutrVals;
    }

    public static void main(String... args) {
        out.println(getNutrition("04913207", null));
        out.println(getNutrition("04043108", null));
    }
}
