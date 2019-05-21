package com.mike.projectboxscore;

import android.os.SystemClock;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Handler;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() {
        UiController uiController;
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.sign_in_button), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.loginConstraintLayout),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
               withId(R.id.button_new_game));
        SystemClock.sleep(5000);
        appCompatButton2.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.teamRecyclerView),
                        childAtPosition(
                                withId(R.id.constraintLayoutNewGame),
                                3)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.playerRecyclerView),
                        childAtPosition(
                                withId(R.id.constraintLayoutNewGame),
                                1)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.playerRecyclerView),
                        childAtPosition(
                                withId(R.id.constraintLayoutNewGame),
                                1)));
        recyclerView3.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.playerRecyclerView),
                        childAtPosition(
                                withId(R.id.constraintLayoutNewGame),
                                1)));
        recyclerView4.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction recyclerView5 = onView(
                allOf(withId(R.id.playerRecyclerView),
                        childAtPosition(
                                withId(R.id.constraintLayoutNewGame),
                                1)));
        recyclerView5.perform(actionOnItemAtPosition(3, click()));

        ViewInteraction recyclerView6 = onView(
                allOf(withId(R.id.playerRecyclerView),
                        childAtPosition(
                                withId(R.id.constraintLayoutNewGame),
                                1)));
        recyclerView6.perform(actionOnItemAtPosition(4, click()));

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextOpponent),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayoutNewGame),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("rt"), closeSoftKeyboard());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
