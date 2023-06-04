package com.example.compose.old

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.old.theme.ComposeTestTheme

@Composable
fun NamePicker(
    header: String,
    names: List<String>,
    modifier: Modifier = Modifier,
    onNameClicked: (String) -> Unit = {}
) {
    Surface(modifier.fillMaxSize()) {
        Column {
            // this will recompose when [header] changes, but not when [names] changes
            Text(
                text = header,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Divider()

            // LazyColumn is the Compose version of a RecyclerView.
            // The lambda passed to items() is similar to a RecyclerView.ViewHolder.
            LazyColumn {
                items(names, key = { it }) { name ->
                    // When an item's [name] updates, the adapter for that item
                    // will recompose. This will not recompose when [header] changes
                    NamePickerItem(name, onNameClicked)
                }
            }
        }
    }
}

@Composable
private fun NamePickerItem(name: String, onClicked: (String) -> Unit) {
//    Surface(.clickable(onClick = { onClicked(name) })
    Text(
        text = name,
        modifier = Modifier
            .clickable(onClick = { onClicked(name) })
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Preview(device = Devices.PIXEL_3, showSystemUi = true)
@Composable
fun NamePickerPreview() {
    ComposeTestTheme {
        val items = mutableListOf<String>().apply {
            repeat(20) {
                add("List item $it")
            }
        }
        NamePicker(header = "Header", names = items)
    }
}