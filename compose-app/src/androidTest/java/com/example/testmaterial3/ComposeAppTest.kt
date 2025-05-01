package com.example.testmaterial3

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import com.example.compose.ui.ComposeActivity
import org.junit.Rule
import org.junit.Test

class ComposeAppTest {

    private val chatTitle = "Compose chat"
    private val authorName = "Anastasiia"

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComposeActivity>()

    @Test
    fun testActivityScenario() {
        composeTestRule.onNodeWithText(chatTitle)
            .assertIsDisplayed()

        val itemsCount = 13
        composeTestRule
            .onNodeWithTag("ScrollableItemTag")
            .performScrollToIndex(itemsCount - 1)

        composeTestRule.onAllNodesWithText(authorName).get(2).let {
            it.performClick()
            it.performClick()
        }

        composeTestRule.onNodeWithText("Compose chat")
            .performClick()
    }

}