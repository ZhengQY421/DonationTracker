/**
 * @author: John Thompson
 * @decription: Tests DonationActivity
 */
package edu.gatech.cs2340.youngmoney;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
        onView(withId(R.id.location_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.buttonDonation)).perform(click());

        //Test when field is empty
        onView(withId(R.id.create)).perform(click());
        onView(withId(R.id.item)).check(matches(hasErrorText("This field is required")));

        onView(withId(R.id.item)).perform(typeText("Dog"), closeSoftKeyboard());
        onView(withId(R.id.user)).perform(typeText("Big Man"), closeSoftKeyboard());
        onView(withId(R.id.date)).perform(typeText("11/9/18"), closeSoftKeyboard());
        onView(withId(R.id.fulldesc)).perform(typeText("Super nice dog"), closeSoftKeyboard());
        onView(withId(R.id.value)).perform(typeText("13"), closeSoftKeyboard());
        onView(withId(R.id.category)).perform(typeText("Animal"), closeSoftKeyboard());

        //Test when everything correct
        onView(withId(R.id.create)).perform(click());
        onView(withId(R.id.location_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.donations)).check(new RecyclerViewItemCountAssertion(1));
    }

    public class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int expectedCount;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), is(expectedCount));
        }
    }

}
