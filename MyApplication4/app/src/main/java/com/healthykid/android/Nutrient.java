package com.healthykid.android;

/**
 * An enum for all the nutrients
 */
public enum Nutrient {
    CALCIUM("calcium", R.string.calcium, "%"),

    CALORIES("calories", R.string.calories, ""),
    FAT_CALORIES("fat_calories", R.string.fat_calories, ""),


    CHOLESTEROL("cholesterol", R.string.cholesterol, "mg"),
    FIBER("dietary_fiber", R.string.fiber, "g"),
    IRON("iron", R.string.iron, "%"),
    POTASSIUM("potassium", R.string.potassium, "mg"),
    PROTEIN("protein", R.string.protein, "g"),
    SATURATED_FAT("sat_fat", R.string.sat_fat, "g"),
    SODIUM("sodium", R.string.sodium, "mg"),
    SUGAR("sugars", R.string.sugar, "g"),
    CARBOHYDRATES("total_carb", R.string.carb, "g"),
    FAT("total_fat", R.string.fat, "g"),
    TRANS_FAT("trans_fat", R.string.trans_fat, "g"),
    VITAMIN_A("vitamin_a", R.string.vitamin_a, "%"),
    VITAMIN_C("vitamin_c", R.string.vitamin_c, "%"),
    SERVINGS("servings", R.string.servings, "");

    private final String request;
    private final int display;
    private final String unit;

    Nutrient(String request, int display, String unit) {
        this.request = request;
        this.display = display;
        this.unit = unit;
    }

    public String getRequest() {
        return request;
    }

    public int getDisplay() {
        return display;
    }

    public String getUnit() {
        return unit;
    }
}
