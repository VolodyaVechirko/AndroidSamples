package com.example.testmaterial3

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.matcher.IntentMatchers.isInternal
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.example.compose.MainActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AppInstrumentedTest {

    @get:Rule
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.INTERNET,
        )

    @Before
    fun preCondition() {
        Intents.init()
    }

    @After
    fun postCondition() {
        Intents.release()
    }

    @Test
    fun runTest() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.testmaterial3", appContext.packageName)

        val intent = appContext.packageManager
            .getLaunchIntentForPackage(appContext.packageName)
        appContext.startActivity(intent)

        intended(hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun testActivityScenarioIntent() {
        ActivityScenario.launch(MainActivity::class.java)

        intending(not(isInternal())).respondWith(
            Instrumentation.ActivityResult(Activity.RESULT_OK, null)
        )
        onView(withId(R.id.button_first)).perform(click())

        val phoneNumber = "1234567890"
        onView(withId(R.id.phone_text)).perform(typeText(phoneNumber))
        onView(withId(R.id.button_call)).perform(click())
        intended(
            allOf(
                hasAction(Intent.ACTION_DIAL),
                hasData(Uri.parse("tel:$phoneNumber")),
            )
        )
    }

    @Test
    fun testActivityScenario() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.button_first)).perform(click())
        intended(hasComponent(MainActivity::class.java.name))

//        scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun testActivityScenario2() {
        ActivityScenario.launch(MainActivity::class.java).onActivity {

        }
        onView(withId(R.id.button_first)).perform(click())
        intended(hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun testLaunchTestActivity() {
        ActivityScenario.launch(TestActivity::class.java).onActivity {

        }
        onView(withId(R.id.button_first)).perform(click())
        intended(hasComponent(TestActivity::class.java.name))
    }
}