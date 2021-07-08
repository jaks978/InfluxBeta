package com.example.influxbeta

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SplashscreenTest
{
    @Before
    fun setUp() {
        ActivityScenario.launch(Splashscreen::class.java)
    }

    @Test
    fun Activity_inViewMainTest()
    {

        Espresso.onView(withId(R.id.main)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun Activity_inViewAnimationTest()
    {
        ActivityScenario.launch(Splashscreen::class.java)
        Espresso.onView(withId(R.id.animationView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun Activity_inViewAppNameTest()
    {
        ActivityScenario.launch(Splashscreen::class.java)
        Espresso.onView(withId(R.id.app_name)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}