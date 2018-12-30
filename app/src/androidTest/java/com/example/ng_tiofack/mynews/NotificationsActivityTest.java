package com.example.ng_tiofack.mynews;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ng_tiofack.mynews.controler.activities.NotificationsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by NG-TIOFACK on 12/28/2018.
*/

@RunWith(AndroidJUnit4.class)
public class NotificationsActivityTest {

    @Rule
    public ActivityTestRule<NotificationsActivity> rule = new ActivityTestRule<>(NotificationsActivity.class,true,false);

    @Test
    public void  ClassUnderTest() {
        rule.launchActivity(new Intent());
        onView(withId(R.id.editText)).check(matches(withHint(R.string.search_query_term)));
        onView(withText(R.string.business)).check(matches(isDisplayed()));
        onView(withText(R.string.entrepreneurs)).check(matches(isDisplayed()));
        onView(withText(R.string.politics)).check(matches(isDisplayed()));
        onView(withText(R.string.travel)).check(matches(isDisplayed()));
        onView(withText(R.string.sports)).check(matches(isDisplayed()));
    }


    @Test
    public void  MethodUnderTest() {
        rule.launchActivity(new Intent());

        onView(withId(R.id.editText)).check(matches(withHint(R.string.search_query_term)));
        onView(withText(R.string.business)).check(matches(isDisplayed()));
        onView(withText(R.string.entrepreneurs)).check(matches(isDisplayed()));
        onView(withText(R.string.politics)).check(matches(isDisplayed()));
        onView(withText(R.string.travel)).check(matches(isDisplayed()));
        onView(withText(R.string.sports)).check(matches(isDisplayed()));
    }
}
