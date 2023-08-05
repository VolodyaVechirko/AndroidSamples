package com.vvechirko.camerax.friends

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vvechirko.camerax.ui.AppTheme

@Composable
fun FriendsView(
//    viewModel: SearchViewModel = remember { SearchViewModel() }
    viewModel: SearchViewModel = viewModel()
) {
    Column {
        SearchView(
            text = viewModel.query,
            placeholder = "Search",
            onTextChange = viewModel::onTextChange
        )

        LazyColumn {
            items(viewModel.searchResults) { name ->
                ListItem(text = name, onItemClicked = { })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(text: String, placeholder: String, onTextChange: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        placeholder = { Text(text = placeholder) },
        textStyle = MaterialTheme.typography.bodyMedium,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        leadingIcon = {
            Icon(Icons.Filled.Search, null, tint = MaterialTheme.colorScheme.secondary)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun ListItem(text: String, onItemClicked: (String) -> Unit) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClicked(text) })
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewPostCard() {
    val vm = SearchViewModel().apply {
        query = "Test text"
        searchResults = listOf("Anastasiia", "Volodymyr", "Slava")
    }
    AppTheme {
        Surface {
            FriendsView(vm)
        }
    }
}