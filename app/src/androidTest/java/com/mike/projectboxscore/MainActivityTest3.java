package com.mike.projectboxscore;

import android.os.SystemClock;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest3 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest3() {
//        ViewInteraction appCompatButton = onView(
//                allOf(withId(R.id.sign_in_button), withText("Login"),
//                        childAtPosition(
//                                allOf(withId(R.id.loginConstraintLayout),
//                                        childAtPosition(
//                                                withId(R.id.container),
//                                                0)),
//                                2),
//                        isDisplayed()));
//        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button_new_game), withText("new game"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                1),
                        isDisplayed()));
        SystemClock.sleep(3000);
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
        appCompatEditText.perform(replaceText("Rockets"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextTournamnet),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayoutNewGame),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                8),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("NBA "), closeSoftKeyboard());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.imageViewNext),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayoutNewGame),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                9),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.buttonSteal),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                8),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.buttonBlock),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                26),
                        isDisplayed()));
        appCompatImageView3.perform(click());

        ViewInteraction appCompatImageView4 = onView(
                allOf(withId(R.id.buttonTurnOver),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageView4.perform(click());

        ViewInteraction appCompatImageView5 = onView(
                allOf(withId(R.id.buttonAssist),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageView5.perform(click());

        ViewInteraction appCompatImageView6 = onView(
                allOf(withId(R.id.buttonOffensiveRebound),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatImageView6.perform(click());

        ViewInteraction appCompatImageView7 = onView(
                allOf(withId(R.id.buttonDefensiveRebound),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                9),
                        isDisplayed()));
        appCompatImageView7.perform(click());

        ViewInteraction appCompatImageView8 = onView(
                allOf(withId(R.id.button2Pts),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                25),
                        isDisplayed()));
        appCompatImageView8.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.buttonMade), withText(" made "),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatImageView9 = onView(
                allOf(withId(R.id.button2Pts),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                25),
                        isDisplayed()));
        appCompatImageView9.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.buttonMiss), withText(" miss "),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatImageView10 = onView(
                allOf(withId(R.id.button3Pts),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                13),
                        isDisplayed()));
        appCompatImageView10.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.buttonMade), withText(" made "),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatImageView11 = onView(
                allOf(withId(R.id.button3Pts),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                13),
                        isDisplayed()));
        appCompatImageView11.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.buttonMiss), withText(" miss "),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatImageView12 = onView(
                allOf(withId(R.id.buttonFreeThrow),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                24),
                        isDisplayed()));
        appCompatImageView12.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.buttonMade), withText(" made "),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatImageView13 = onView(
                allOf(withId(R.id.buttonFreeThrow),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                24),
                        isDisplayed()));
        appCompatImageView13.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.buttonMiss), withText(" miss "),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction recyclerView7 = onView(
                allOf(withId(R.id.recyclerview_onCourt_players),
                        childAtPosition(
                                withId(R.id.container),
                                15)));
        recyclerView7.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction appCompatImageView14 = onView(
                allOf(withId(R.id.buttonFoul),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                23),
                        isDisplayed()));
        appCompatImageView14.perform(click());

        ViewInteraction appCompatImageView15 = onView(
                allOf(withId(R.id.buttonSub),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                22),
                        isDisplayed()));
        appCompatImageView15.perform(click());

        ViewInteraction recyclerView8 = onView(
                allOf(withId(R.id.subPlayers),
                        childAtPosition(
                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                1)));
        recyclerView8.perform(actionOnItemAtPosition(1, click()));
        SystemClock.sleep(5000);
        ViewInteraction appCompatImageView16 = onView(
                allOf(withId(R.id.buttonTutorial),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                7),
                        isDisplayed()));
        appCompatImageView16.perform(click());

        ViewInteraction appCompatImageView17 = onView(
                allOf(withId(R.id.imageViewDismiss),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()));
        appCompatImageView17.perform(click());

        ViewInteraction appCompatImageView18 = onView(
                allOf(withId(R.id.buttonBoxScore),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                21),
                        isDisplayed()));
        appCompatImageView18.perform(click());
        SystemClock.sleep(2000);
        pressBack();
        ViewInteraction appCompatImageView19 = onView(
                allOf(withId(R.id.buttonExit),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                20),
                        isDisplayed()));
        appCompatImageView19.perform(click());
        SystemClock.sleep(2000);
        ViewInteraction appCompatButton9 = onView(
                allOf(withId(android.R.id.button1), withText("Yes"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton9.perform(scrollTo(), click());
        SystemClock.sleep(3000);
        ViewInteraction appCompatImageView20 = onView(
                allOf(withId(R.id.imageViewHome),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                30),
                        isDisplayed()));
        appCompatImageView20.perform(click());
        SystemClock.sleep(3000);
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
