package com.example.app;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.out;

class NutritionAPIRequester {
    public static final String[] NUTRIENTS = new String[]{"calcium",
            "calories", "cholesterol", "dietary_fiber", "fat_calories", "iron",
            "potassium", "protein", "sat_fat", "sodium", "sugars",
            "total_carb", "total_fat", "trans_fat", "vitamin_a", "vitamin_c",
            "servings"};
    private static final String KEY = "v9AM5yVkdkdPUi7OnuzeKAbCideeimaG0nLZFP6t";
    private static final String URL = "http://api.v3.factual.com/t/products-cpg-nutrition";

    public static ArrayList<String> getNutrition(String upc) {
        String query = "";
        String[] vars = new String[]{"q", "KEY"};
        String[] values = new String[]{upc, KEY};
        // 04913207 is the UPC code for a sprite can
        // 04043108 is m&m's

        for (int i = 0; i < vars.length; i++)
            query += '&' + vars[i] + '=' + values[i];
        query = query.substring(1);

        RetrieveNutritionTask r = new RetrieveNutritionTask();
        r.execute(URL + "?" + query);

        return r.getNutrVals();
    }

    public static void main(String... args) throws IOException {
        out.println(getNutrition("04913207"));
        out.println(getNutrition("04043108"));
    }
}