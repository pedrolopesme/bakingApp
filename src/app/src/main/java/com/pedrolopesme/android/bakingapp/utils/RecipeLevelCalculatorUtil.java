package com.pedrolopesme.android.bakingapp.utils;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.model.Recipe;

/**
 * Recipe Level Calculator
 */
public final class RecipeLevelCalculatorUtil {

    /**
     * Calculates recipe level according to recipe's steps.
     * <p>
     * Between 1 and 6 : easy; 7 and 10 : medium; greater than 11 : hard
     *
     * @param recipe
     * @return string resource id
     */
    public static Integer calculate(Recipe recipe) {
        if (recipe != null && recipe.getSteps() != null) {
            if (recipe.getSteps().size() >= 1 && recipe.getSteps().size() <= 6)
                return R.string.level_easy;
            else if (recipe.getSteps().size() >= 7 && recipe.getSteps().size() <= 10)
                return R.string.level_medium;
            else if (recipe.getSteps().size() >= 11)
                return R.string.level_hard;
        }
        return null;
    }
}
