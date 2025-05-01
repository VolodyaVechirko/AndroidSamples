package com.example.compose.data

object ChatData {

    fun getAll() = listOf(
        Message(
            "Anastasiia",
            "Test...Test...Test..."
        ),
        Message(
            "Anastasiia",
            """List of Android versions:
            |Android KitKat (API 19)
            |Android Lollipop (API 21)
            |Android Marshmallow (API 23)
            |Android Nougat (API 24)
            |Android Oreo (API 26)
            |Android Pie (API 28)
            |Android 10 (API 29)
            |Android 11 (API 30)
            |Android 12 (API 31)""".trim()
        ),
        Message(
            "Anastasiia",
            """I think Kotlin is my favorite programming language.
            |It's so much fun!""".trim()
        ),
        Message(
            "Anastasiia",
            "Searching for alternatives to XML layouts..."
        ),
        Message(
            "Anastasiia",
            """Hey, take a look at Jetpack Compose, it's great!
            |It's the Android's modern toolkit for building native UI.
            |It simplifies and accelerates UI development on Android.
            |Less code, powerful tools, and intuitive Kotlin APIs :)""".trim()
        ),
        Message(
            "Anastasiia",
            "It's available from API 21+ :)"
        ),
        Message(
            "Anastasiia",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
        ),
        Message(
            "Anastasiia",
            "Android Studio next version's name is Arctic Fox"
        ),
        Message(
            "Anastasiia",
            "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
        ),
        Message(
            "Anastasiia",
            "I didn't know you can now run the emulator directly from Android Studio"
        ),
        Message(
            "Anastasiia",
            "Compose Previews are great to check quickly how a composable layout looks like"
        ),
        Message(
            "Anastasiia",
            "Previews are also interactive after enabling the experimental setting"
        ),
        Message(
            "Anastasiia",
            "Have you tried writing build.gradle with KTS?"
        ),
    )
}
