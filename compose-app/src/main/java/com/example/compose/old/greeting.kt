package com.example.compose.old

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.compose.old.theme.ComposeTestTheme
import com.example.testmaterial3.R

@Composable
fun Greeting(title: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Surface(modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .height(100.dp)
                        .align(Alignment.Center)
                        .clickable { },
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_android),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                        alignment = Alignment.Center
                    )
                }
            }

            SelectionContainer(Modifier.fillMaxWidth()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Hello $title!", style = MaterialTheme.typography.h6)
                    Text(
                        text = "Jetpack Compose is built around composable functions. These functions let you define your app's UI programmatically by describing its shape and data dependencies, rather than focusing on the process of the UI's construction.",
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

//            val helloViewModel = HelloViewModel()
//            val name: String by helloViewModel.name.observeAsState("")
            var name by rememberSaveable { mutableStateOf("") }
            HelloContent(name = name, onNameChanged = { name = it })
            Spacer(modifier = Modifier.height(56.dp))

            Button(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(),
                onClick = onClick
            ) {
                Text(text = "Click me")
            }

            Box(modifier = Modifier.weight(1f)) {
                TextButton(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onClick = { }
                ) {
                    Text(text = "Visit web-site")
                }
            }
        }
    }
}

@Composable
fun HelloContent(name: String, onNameChanged: (String) -> Unit) {
    Column {
        if (name.isNotEmpty()) {
            Text(
                text = "Hello, ${name}!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.h4
            )
        }
        OutlinedTextField(
            value = name,
            onValueChange = onNameChanged,
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {})
        )
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_3)
@Composable
fun GreetingPreview() {
    ComposeTestTheme {
        Greeting("Android")
    }
}

// ---
class HelloViewModel : ViewModel() {
    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    fun onNameChange(newName: String) {
        _name.value = newName
    }
}