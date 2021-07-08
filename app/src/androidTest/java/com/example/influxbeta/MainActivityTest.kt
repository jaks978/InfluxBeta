package com.example.influxbeta

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.influxbeta.view.activity.MainActivity
import org.junit.Before
import org.junit.Test

class MainActivityTest {
    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun Activity_inViewMainTest() {
        Espresso.onView(ViewMatchers.withId(R.id.main))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun Activity_inViewBottomLayoutTest() {
        Espresso.onView(ViewMatchers.withId(R.id.bottom_layout)).check(
            ViewAssertions.matches(
                ViewMatchers.withEffectiveVisibility(
                    ViewMatchers.Visibility.GONE

                )
            )
        )
    }

    @Test
    fun Activity_inViewTabLayoutTest() {
        Espresso.onView(ViewMatchers.withId(R.id.tab_layout)).check(
            ViewAssertions.matches(
                ViewMatchers.withEffectiveVisibility(
                    ViewMatchers.Visibility.VISIBLE
                )
            )
        )
    }

    @Test
    fun Activity_inViewViewPagerTest() {
        Espresso.onView(ViewMatchers.withId(R.id.pager)).check(
            ViewAssertions.matches(
                ViewMatchers.withEffectiveVisibility(
                    ViewMatchers.Visibility.VISIBLE
                )
            )
        )
    }


}