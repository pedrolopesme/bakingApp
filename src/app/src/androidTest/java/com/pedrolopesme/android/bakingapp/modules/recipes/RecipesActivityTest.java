package com.pedrolopesme.android.bakingapp.modules.recipes;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.pedrolopesme.android.bakingapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.pedrolopesme.android.bakingapp.matchers.ActionBarMatcher.matchToolbarTitle;
import static org.hamcrest.CoreMatchers.allOf;


@RunWith(AndroidJUnit4.class)
public class RecipesActivityTest {

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityTestRule = new ActivityTestRule<>(RecipesActivity.class);

    @Before
    public void init() {
        mActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void testRecipesAreDisplayed() {
        onView(withId(R.id.fl_recipes_container))
                .check(matches((isDisplayed())));

        onView(allOf(withId(R.id.rv_recipes), isDisplayed()))
                .check(matches((isDisplayed())));
    }


    @Test
    public void testToolbarTitle() {
        CharSequence title = InstrumentationRegistry.getTargetContext().getString(R.string.activity_recipes);
        matchToolbarTitle(title);
    }


}
