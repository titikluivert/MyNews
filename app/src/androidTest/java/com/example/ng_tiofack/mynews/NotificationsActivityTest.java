package com.example.ng_tiofack.mynews;

import android.content.Intent;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ng_tiofack.mynews.controler.activities.NotificationsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.IsNot.not;

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
        onView(withId(R.id.editText_notiification)).check(matches(withHint(R.string.search_query_term)));
        onView(withText(R.string.business)).check(matches(isDisplayed()));
        onView(withText(R.string.entrepreneurs)).check(matches(isDisplayed()));
        onView(withText(R.string.politics)).check(matches(isDisplayed()));
        onView(withText(R.string.travel)).check(matches(isDisplayed()));
        onView(withText(R.string.sports)).check(matches(isDisplayed()));
    }


    @Test
    public void  viewsOnUITest() {
        rule.launchActivity(new Intent());
        onView(withId(R.id.checkBox1)).check(matches(hasSibling(withId(R.id.checkBox2))));
        onView(withId(R.id.checkBox1)).check(matches(hasSibling(withId(R.id.checkBox3))));
        onView(withId(R.id.checkBox5)).check(matches(hasSibling(withId(R.id.checkBox4))));
        onView(withId(R.id.checkBox5)).check(matches(hasSibling(withId(R.id.checkBox6))));
        onView(withId(R.id.notification_layout_container)).check(matches(hasDescendant(withId(R.id.checkBox6))));
        onView(withId(R.id.enable_notifications)).check(matches(isEnabled()));

    }

    @Test
    public void  actionWithViewsTest() {
        rule.launchActivity(new Intent());
        onView(withId(R.id.editText_notiification)).perform(typeText("macron"));
        onView(withId(R.id.checkBox3)).check(matches(not(isChecked()))).perform(click());
        onView(withId(R.id.enable_notifications)).perform(click());

    }
}