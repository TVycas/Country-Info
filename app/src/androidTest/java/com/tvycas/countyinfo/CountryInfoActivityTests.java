package com.tvycas.countyinfo;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.tvycas.countyinfo.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CountryInfoActivityTests {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testCountryInfoActivityTextViews_Lithuania() {
        String countryName = "Lithuania";
        String capital = "Capital: Vilnius";
        String currency = "Currency: Euro (Symbol: €)";
        String language = "Language(s): Lithuanian";
        String nativeName = "Native name: Lietuva";
        String countryCode = "Country code: LTU";

        onView(withId(R.id.search_view)).perform(typeText(countryName));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.country_list_recyclerview)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(countryName)), click()));

        onView(withId(R.id.name)).check(matches(withText(countryName)));
        onView(withId(R.id.capital)).check(matches(withText(capital)));
        onView(withId(R.id.currency)).check(matches(withText(currency)));
        onView(withId(R.id.languages)).check(matches(withText(language)));
        onView(withId(R.id.native_name)).check(matches(withText(nativeName)));
        onView(withId(R.id.code)).check(matches(withText(countryCode)));

    }

    @Test
    public void testCountryInfoActivityTextViews_Germany() {
        String countryName = "Germany";
        String capital = "Capital: Berlin";
        String currency = "Currency: Euro (Symbol: €)";
        String language = "Language(s): German";
        String nativeName = "Native name: Deutschland";
        String countryCode = "Country code: DEU";

        onView(withId(R.id.search_view)).perform(typeText(countryName));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.country_list_recyclerview)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(countryName)), click()));

        onView(withId(R.id.name)).check(matches(withText(countryName)));
        onView(withId(R.id.capital)).check(matches(withText(capital)));
        onView(withId(R.id.currency)).check(matches(withText(currency)));
        onView(withId(R.id.languages)).check(matches(withText(language)));
        onView(withId(R.id.native_name)).check(matches(withText(nativeName)));
        onView(withId(R.id.code)).check(matches(withText(countryCode)));

    }

    @Test
    public void testSearchAndMoveTwoCountries_PolandArgentina() {
        String countryName1 = "Poland";
        String countryName2 = "Argentina";

        // Navigate to CountryInfoActivity
        onView(withId(R.id.search_view)).perform(typeText(countryName1));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.country_list_recyclerview)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(countryName1)), click()));

        //Check if it's the right country and the right activity
        onView(withId(R.id.name)).check(matches(withText(countryName1)));

        // Move up
        onView(withContentDescription("Navigate up")).perform(click());

        onView(withId(R.id.search_view)).perform(typeText(countryName2));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.country_list_recyclerview)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(countryName2)), click()));

        //Check if it's the right country and the right activity
        onView(withId(R.id.name)).check(matches(withText(countryName2)));

        // Move up
        onView(withContentDescription("Navigate up")).perform(click());

    }
}
