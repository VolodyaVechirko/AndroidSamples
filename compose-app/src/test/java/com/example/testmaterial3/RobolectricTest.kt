package com.example.testmaterial3

import android.content.Intent
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.test.core.app.ActivityScenario
import com.example.compose.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import com.example.testmaterial3.R as TestR

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class RobolectricTest {

    @Test
    fun runTest1() {
//        val activity = Robolectric.setupActivity(MainActivity::class.java)
        val activity = Robolectric.buildActivity(MainActivity::class.java).setup().get()

        val button = activity.findViewById<Button>(TestR.id.button_first)
        val toolbar = activity.findViewById<Toolbar>(TestR.id.toolbar)
        assertThat(toolbar.title.toString(), equalTo("First Fragment"))

        button.performClick()
        assertThat(toolbar.title.toString(), equalTo("Second Fragment"))

        val fab = activity.findViewById<FloatingActionButton>(TestR.id.fab)
        assertThat(fab.contentDescription.toString(), equalTo("TestMaterial3"))

        fab.performClick()
        val actual = shadowOf(RuntimeEnvironment.getApplication()).nextStartedActivity
        assertEquals(Intent.ACTION_VIEW, actual.action)
        assert(actual.data.toString().contains("facebook"))

//        shadowOf(RuntimeEnvironment.getApplication()).shownToasts
//        shadowOf(RuntimeEnvironment.getApplication()).nextStartedService
//        shadowOf(RuntimeEnvironment.getApplication()).denyPermissions()
    }

    @Test
    fun runTest2() {
        ActivityScenario.launch(MainActivity::class.java).onActivity {
            val button = it.findViewById<Button>(TestR.id.button_first)
            val toolbar = it.findViewById<Toolbar>(TestR.id.toolbar)
            assertThat(toolbar.title.toString(), equalTo("First Fragment"))

            button.performClick()
            assertThat(toolbar.title.toString(), equalTo("Second Fragment"))

            val fab = it.findViewById<FloatingActionButton>(TestR.id.fab)
            assertThat(fab.contentDescription.toString(), equalTo("TestMaterial3"))

            fab.performClick()
            val actual = shadowOf(RuntimeEnvironment.getApplication()).nextStartedActivity
            assertEquals(Intent.ACTION_VIEW, actual.action)
            assert(actual.data.toString().contains("facebook"))
        }
    }
}