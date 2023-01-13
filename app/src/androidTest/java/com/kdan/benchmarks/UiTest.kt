package com.kdan.benchmarks

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class UiTest {

    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun dialogIsDisplayed() {
        Espresso.onView(withId(R.id.floating_button))
            .perform(ViewActions.click())

        Espresso.onView(withId(R.id.dialog_fragment_coll_size))
            .check(matches(isDisplayed()))
    }

    @Test
    fun fragmentCollectionsIsDisplayed() {
        Espresso.onView(ViewMatchers.withText("Collections"))
            .perform(ViewActions.click())

        Espresso.onView(withId(R.id.fragment_collections))
            .check(matches(isDisplayed()))
    }

    @Test
    fun navigationBetweenFragmentsClickingTabs() {
        Espresso.onView(ViewMatchers.withText("Maps"))
            .perform(ViewActions.click())

        Espresso.onView(withId(R.id.fragment_maps))
            .check(matches(isDisplayed()))
    }

    @Test
    fun navigationBetweenFragmentsSwipingTabs() {
        Espresso.onView(ViewMatchers.withText("Maps"))
            .perform(ViewActions.swipeLeft())

        Espresso.onView(withId(R.id.fragment_maps))
            .check(matches(isDisplayed()))
    }
}