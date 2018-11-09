/**
 * @author: Oliver Zheng
 * @decription: Tests LoginActivity
 */
package edu.gatech.cs2340.youngmoney;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import edu.gatech.cs2340.youngmoney.activity.HomeActivity;
import edu.gatech.cs2340.youngmoney.activity.LoginActivity;


public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    /**
     * Tests mainly the attemptLogin method and UserLoginTask of LoginActivity
     */
    @Test
    public void checkLogin() {

        //Test when both fields are empty
        onView(withId(R.id.username_sign_in_button)).perform(click());
        onView(withId(R.id.username)).check(matches(hasErrorText("This field is required")));

        //Test when invalid user name
        onView(withId(R.id.username)).perform(typeText("Pika"), closeSoftKeyboard());
        onView(withId(R.id.username_sign_in_button)).perform(click());
        onView(withId(R.id.username)).check(matches(hasErrorText("This username is invalid")));
        onView(withId(R.id.username)).perform(clearText());

        //Test when empty password field
        onView(withId(R.id.username)).perform(typeText("user"), closeSoftKeyboard());
        onView(withId(R.id.username_sign_in_button)).perform(click());
        onView(withId(R.id.password)).check(matches(hasErrorText("This field is required")));

        //Test when incorrect password
        onView(withId(R.id.password)).perform(typeText("word"), closeSoftKeyboard());
        onView(withId(R.id.username_sign_in_button)).perform(click());
        onView(withId(R.id.password)).check(matches(hasErrorText("This password is incorrect")));
        onView(withId(R.id.password)).perform(clearText());

        //Test when everything correct
        onView(withId(R.id.password)).perform(typeText("pass"), closeSoftKeyboard());
        onView(withId(R.id.username_sign_in_button)).perform(click());
        //TODO: Figure out how to check correct intent
        //intended(toPackage("edu.gatech.cs2340.youngmoney.activity"));
    }
}
