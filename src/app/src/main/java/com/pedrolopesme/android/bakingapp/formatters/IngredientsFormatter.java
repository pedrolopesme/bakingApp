package com.pedrolopesme.android.bakingapp.formatters;

import com.pedrolopesme.android.bakingapp.models.Ingredient;

import java.util.List;

public class IngredientsFormatter {

    private final List<Ingredient> ingredients;

    public IngredientsFormatter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Formats the ingredients list to a simple list broken by break lines
     *
     * @return
     */
    public String simpleListFormat() {
        StringBuilder sb = new StringBuilder();
        if (ingredients != null) {
            String separator = System.getProperty("line.separator");
            ;
            for (Ingredient ingredient : ingredients) {
                sb.append(ingredient.getIngredient()).append(separator);
            }
        }
        return sb.toString();
    }

}
