package com.example.ng_tiofack.mynews;

import android.content.Intent;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ng_tiofack.mynews.controler.activities.NotificationsActivity;
import com.example.ng_tiofack.mynews.controler.activities.SearchActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by NG-TIOFACK on 12/28/2018.
 */

@RunWith(AndroidJUnit4.class)
public class SearchActivityTest {

    @Rule
    public ActivityTestRule<SearchActivity> rule = new ActivityTestRule<>(SearchActivity.class, true, false);

    @Test
    public void  actionWithViewsTest() {
        rule.launchActivity(new Intent());
        onView(withId(R.id.editText_notiification)).perform(typeText("macron"));
        onView(withId(R.id.checkBox3)).check(matches(not(isChecked()))).perform(click());
        onView(withId(R.id.enable_notifications)).perform(click());

    }


}
