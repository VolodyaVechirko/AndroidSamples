package com.example.compose.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.data.ChatData
import com.example.compose.data.Message

@Composable
fun Conversation(messages: List<Message>, modifier: Modifier = Modifier) {
    Surface {
        LazyColumn(modifier = modifier) {
            items(messages) { message ->
                MessageCard(message)
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewConversation() {
    MyMaterialTheme {
        Conversation(ChatData.getAll())
    }
}

