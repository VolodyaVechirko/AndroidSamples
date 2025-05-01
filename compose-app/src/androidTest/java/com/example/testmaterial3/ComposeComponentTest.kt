package com.example.testmaterial3

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ComposeComponentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testButtonVisibilityAndClick() {
        // Launch the Compose screen/activity
        composeTestRule.setContent {
            // Compose UI code here
            Button(
                onClick = { /* Button click action */ }
            ) {
                Text("Click Me")
            }
        }

        // Check if the button is displayed
        composeTestRule.onNodeWithText("Click Me").assertIsDisplayed()

        // Perform a click action on the button
        composeTestRule.onNodeWithText("Click Me").performClick()
    }
}