package com.pedrolopesme.android.bakingapp.formatters;


import com.pedrolopesme.android.bakingapp.models.Ingredient;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class IngredientsFormatterTest {

    @Test
    public void testNullIngredientsList() {
        String expectedSimpleFormat = "";
        String generatedFormat = new IngredientsFormatter(null).simpleListFormat();
        assertEquals(expectedSimpleFormat, generatedFormat);
    }

    @Test
    public void testEmptyIgredientsList() {
        String expectedSimpleFormat = "";
        String generatedFormat = new IngredientsFormatter(new ArrayList<Ingredient>()).simpleListFormat();
        assertEquals(expectedSimpleFormat, generatedFormat);
    }

    @Test
    public void testSingleItemIngredientsList() {
        Ingredient dummyIngredient = new Ingredient();
        dummyIngredient.setIngredient("My Dummy Ingredient");

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(dummyIngredient);

        String expectedSimpleFormat = dummyIngredient.getIngredient() + "\n";
        String generatedFormat = new IngredientsFormatter(ingredients).simpleListFormat();
        assertEquals(expectedSimpleFormat, generatedFormat);
    }

    @Test
    public void testMultipleItemIngredientsList() {
        Ingredient dummyIngredient1 = new Ingredient();
        dummyIngredient1.setIngredient("My Dummy Ingredient");

        Ingredient dummyIngredient2 = new Ingredient();
        dummyIngredient2.setIngredient("My Dummy Ingredient");

        Ingredient dummyIngredient3 = new Ingredient();
        dummyIngredient3.setIngredient("My Dummy Ingredient");

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(dummyIngredient1);
        ingredients.add(dummyIngredient2);
        ingredients.add(dummyIngredient3);

        String expectedSimpleFormat = dummyIngredient1.getIngredient() + "\n" +
                dummyIngredient2.getIngredient() + "\n" + dummyIngredient3.getIngredient() + "\n";
        String generatedFormat = new IngredientsFormatter(ingredients).simpleListFormat();
        assertEquals(expectedSimpleFormat, generatedFormat);
    }

}
