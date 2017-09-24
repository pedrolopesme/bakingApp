package com.pedrolopesme.android.bakingapp.utils;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.model.Recipe;
import com.pedrolopesme.android.bakingapp.model.Step;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class RecipeLevelCalculatorUtilTest {

    @Test
    public void nullRecipeTest() {
        assertNull(RecipeLevelCalculatorUtil.calculate(null));
    }

    @Test
    public void recipeWithNullStepsTest() {
        Recipe recipe = new Recipe();
        recipe.setSteps(null);
        assertNull(RecipeLevelCalculatorUtil.calculate(recipe));
    }

    @Test
    public void recipeWithEmptyStepsTest() {
        Recipe recipe = new Recipe();
        recipe.setSteps(new ArrayList<Step>());
        assertNull(RecipeLevelCalculatorUtil.calculate(recipe));
    }

    @Test
    public void recipeWithLessThanSixStepsTest() {
        Recipe recipe = new Recipe();
        recipe.setSteps(createSteps(1));
        Integer levelLabel = R.string.level_easy;
        assertEquals(levelLabel, RecipeLevelCalculatorUtil.calculate(recipe));
    }

    @Test
    public void recipeBetweenSevenAndTenStepsTest() {
        Recipe recipe = new Recipe();
        recipe.setSteps(createSteps(8));
        Integer levelLabel = R.string.level_medium;
        assertEquals(levelLabel, RecipeLevelCalculatorUtil.calculate(recipe));
    }

    @Test
    public void recipeWithElevenOrMoreStepsTest() {
        Recipe recipe = new Recipe();
        recipe.setSteps(createSteps(15));
        Integer levelLabel = R.string.level_hard;
        assertEquals(levelLabel, RecipeLevelCalculatorUtil.calculate(recipe));
    }

    private List<Step> createSteps(int qtt) {
        List<Step> steps = new ArrayList<>();
        for (int c = 0; c < qtt; c++) {
            steps.add(new Step());
        }
        return steps;
    }

}
