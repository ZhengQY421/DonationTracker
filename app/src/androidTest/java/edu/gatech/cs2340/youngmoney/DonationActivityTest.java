/**
 * @author: John Thompson
 * @decription: Tests LoginActivity
 */
package edu.gatech.cs2340.youngmoney;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import edu.gatech.cs2340.youngmoney.activity.LocationActivity;

public class DonationActivityTest {

    @Rule
    public ActivityTestRule<LocationActivity> mActivityRule = new ActivityTestRule<>(LocationActivity.class);

    /**
     * Tests mainly the newDonation method and AddDonationTask of DonationActivity
     */
    @Test
    public void checkAddDonation() {
        //onView(withSpinnerText(startsWith("AFD"))).perform(click());
        onView(withId(R.id.location_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.buttonDonation)).perform(click());

        //Test when both fields are empty
        onView(withId(R.id.create)).perform(click());
        onView(withId(R.id.item)).check(matches(hasErrorText("This field is required")));

        onView(withId(R.id.item)).perform(typeText("Dog"), closeSoftKeyboard());
        onView(withId(R.id.user)).perform(typeText("Big Man"), closeSoftKeyboard());
        onView(withId(R.id.date)).perform(typeText("11/9/18"), closeSoftKeyboard());
        onView(withId(R.id.fulldesc)).perform(typeText("Super nice dog"), closeSoftKeyboard());
        onView(withId(R.id.value)).perform(typeText("$12"), closeSoftKeyboard());
        onView(withId(R.id.category)).perform(typeText("Animal"), closeSoftKeyboard());

        //Test when everything correct
        onView(withId(R.id.create)).perform(click());
    }
}
