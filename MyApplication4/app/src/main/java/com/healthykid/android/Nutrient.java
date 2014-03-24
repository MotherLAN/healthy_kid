package com.healthykid.android;

/**
 * An enum for all the nutrients
 */
public enum Nutrient {
    SERVINGS("servings", R.string.servings, ""),

    CALORIES("calories", R.string.calories, ""),
    FAT_CALORIES("fat_calories", R.string.fat_calories, ""),

    FAT("total_fat", R.string.fat, "g"),
    SATURATED_FAT("sat_fat", R.string.sat_fat, "g"),
    MONOUNSATURATED_FAT("monounsat_fat", R.string.monounsat_fat, "g"),
    POLYUNSATURATED_FAT("polyunsat_fat", R.string.polyunsat_fat, "g"),
    TRANS_FAT("trans_fat", R.string.trans_fat, "g"),

    CHOLESTEROL("cholesterol", R.string.cholesterol, "mg"),
    SODIUM("sodium", R.string.sodium, "mg"),
    POTASSIUM("potassium", R.string.potassium, "mg"),

    CARBOHYDRATES("total_carb", R.string.carb, "g"),
    FIBER("dietary_fiber", R.string.fiber, "g"),
    SUGAR("sugars", R.string.sugar, "g"),

    PROTEIN("protein", R.string.protein, "g"),

    VITAMIN_A("vitamin_a", R.string.vitamin_a, "%"),
    VITAMIN_C("vitamin_c", R.string.vitamin_c, "%"),
    CALCIUM("calcium", R.string.calcium, "%"),
    IRON("iron", R.string.iron, "%");

    private final String request;
    private final int display;
    private final String unit;

    Nutrient(String request, int display, String unit) {
        this.request = request;
        this.display = display;
        this.unit = unit;
    }

    public String getRequest() { return request; }

    public int getDisplay() { return display; }

    public String getUnit() { return unit; }
}
