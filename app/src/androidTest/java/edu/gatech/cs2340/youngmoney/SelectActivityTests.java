/**
 * @author: Shouen Lee
 * @decription: SelectActivity Tests
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
import edu.gatech.cs2340.youngmoney.activity.RegistrationActivity;
 public class SelectActivityTests {
     @Rule
    public ActivityTestRule<RegistrationActivity> mActivityRule = new ActivityTestRule<>(RegistrationActivity.class);
     /**
     * Tests if user selects sign-in or register
     */
    @Test
    public void checkRegister() {
         //Test when both fields are empty
        onView(withId(R.id.SignUpButton)).perform(click());
        onView(withId(R.id.Username)).check(matches(hasErrorText("This field is required")));
         //Test with name
        onView(withId(R.id.Name)).perform(typeText("name"), closeSoftKeyboard());
         //Test when no password
        onView(withId(R.id.Username)).perform(typeText("user"), closeSoftKeyboard());
        onView(withId(R.id.SignUpButton)).perform(click());
        onView(withId(R.id.Password)).check(matches(hasErrorText("This password is incorrect")));
         //Test with password
        onView(withId(R.id.Password)).perform(typeText("word"), closeSoftKeyboard());
         //Test when everything correct
        onView(withId(R.id.SignUpButton)).perform(click());
    }
}
