package com.tvycas.countyinfo;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.tvycas.countyinfo.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class CountrySearchTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);
    private String stringToBeTyped;

    @Test
    public void testSearch_Bulgaria() {
        stringToBeTyped = "Bulgaria";

        onView(withId(R.id.search_view))
                .perform(typeText(stringToBeTyped));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.country_name))
                .check(matches(withText(stringToBeTyped)));
    }

    @Test
    public void testSearch_Antarctica() {
        stringToBeTyped = "Antarctica";

        onView(withId(R.id.search_view))
                .perform(typeText(stringToBeTyped));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.country_name))
                .check(matches(withText(stringToBeTyped)));
    }

    @Test
    public void testSearch_UnitedStates() {
        stringToBeTyped = "United States";
        String countryToLookFor = "United States of America";
        onView(withId(R.id.search_view))
                .perform(typeText(stringToBeTyped));

        Espresso.closeSoftKeyboard();

        onView(allOf(withId(R.id.country_name), withText(countryToLookFor)))
                .check(matches(isDisplayed()));
    }

}
