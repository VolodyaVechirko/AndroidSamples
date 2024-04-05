package com.vvechirko.camerax.more

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vvechirko.camerax.ui.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StickyHeaderScreen() {
    val grouped = remember {
        generateList()
            .groupBy { it.header }
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            IntroView()
        }

        grouped.forEach { (head, groupItems) ->
            stickyHeader(key = head) {
                HeaderView(head)
            }

            items(items = groupItems, key = { it.item }) {
                ItemView(text = it.item)
            }
        }
    }
}

@Composable
private fun IntroView() {
    Text(
        text = "Some intro text... Collections info and others",
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(all = 16.dp)
    )
}

@Composable
private fun HeaderView(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .fillMaxWidth(),
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LazyItemScope.ItemView(text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(bottom = 8.dp)
            .animateItemPlacement(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(all = 16.dp)
        )
    }
}

private class SampleItem(val header: String, val item: String)

private fun generateList(): List<SampleItem> =
    mutableListOf<SampleItem>().apply {
        repeat(100) { i ->
            add(SampleItem("Header ${i / 10 + 1}", "Item ${i + 1}"))
        }
    }

@Preview(device = Devices.PIXEL_3)
@Composable
fun StickyHeaderPreviewScreen() {
    AppTheme {
        Surface {
            StickyHeaderScreen()
        }
    }
}