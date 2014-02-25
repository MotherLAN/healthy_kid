package com.example.app;

import static java.lang.System.out;

import java.io.IOException;
import java.util.ArrayList;

public class NutritionAPIRequester {
	public static final String[] NUTRIENTS = new String[] { "calcium",
			"calories", "cholesterol", "dietary_fiber", "fat_calories", "iron",
			"potassium", "protein", "sat_fat", "sodium", "sugars",
			"total_carb", "total_fat", "trans_fat", "vitamin_a", "vitamin_c",
			"servings" };
	private static final String KEY = "v9AM5yVkdkdPUi7OnuzeKAbCideeimaG0nLZFP6t";
	private static final String URL = "http://api.v3.factual.com/t/products-cpg-nutrition";

	ArrayList<String> nutrVals;
	String upc;

	public NutritionAPIRequester(String upc) throws IOException {
		this.upc = upc;
		nutrVals = new ArrayList<String>();
		getNutrition();
	}

	protected ArrayList<String> getNutrition() throws IOException {
		String query = "";
		String[] vars = new String[] { "q", "KEY" };
		String[] values = new String[] { upc, KEY };
		// 04913207 is the UPC code for a sprite can
		// 04043108 is m&m's

		for (int i = 0; i < vars.length; i++)
			query += '&' + vars[i] + '=' + values[i];
		query = query.substring(1);

		new RetrieveNutritionTask(this).execute(URL + "?" + query);

		// if (connection.getResponseMessage().equals("OK")) {
		//
		// }
		 return nutrVals;
	}

	public void setVals(ArrayList<String> v) {
		nutrVals = v;
	}

	public static void main(String[] args) throws IOException {
		out.println(new NutritionAPIRequester("04913207").getNutrition());
		out.println(new NutritionAPIRequester("04043108").getNutrition());
	}
}