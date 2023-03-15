package com.example.realstatemanagers;

import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class PossessionListTest {

    private MainActivity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @Test
    public void myPossessionsList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withContentDescription("listmypossession"))
                .check(matches(hasMinimumChildCount(1)));
    }


    @Test
    public void addPossession() {
        RecyclerView listTasks = mActivity.findViewById(R.id.list_possession);

        onView(withId(R.id.add_possession)).perform(click());
        onView(withId(R.id.typeAdd_poss)).perform(replaceText("Tâche example"));
        onView(withId(R.id.quesDesc)).perform(replaceText("Tâche example"));
        onView(withId(R.id.quesVal)).perform(replaceText("15825000"));
        onView(withId(R.id.quesSurface)).perform(replaceText("150"));
        onView(withId(R.id.quesNbrePiece)).perform(replaceText("4"));
        onView(withId(R.id.quesAdr)).perform(replaceText("Tâche example"));
        onView(withId(R.id.quesStatut)).perform(replaceText("Tâche example"));
        onView(withId(R.id.quesAgent)).perform(replaceText("Tâche example"));
        onView(withId(R.id.buttonSave)).perform(scrollTo()).perform(click());

        // Check that it contains one element only
        assertThat(listTasks.getAdapter().getItemCount(), equalTo(3));

            // Check that recyclerView is not displayed anymore
        assertThat(listTasks.getVisibility(), equalTo(View.VISIBLE));
    }


}
