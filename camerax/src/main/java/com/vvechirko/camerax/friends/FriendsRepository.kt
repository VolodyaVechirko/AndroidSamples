package com.vvechirko.camerax.friends

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FriendsRepository {

    suspend fun search(query: String): List<String> {
        delay(200L)
        return withContext(Dispatchers.IO) {
            namesList.filter { it.startsWith(query, ignoreCase = true) }
        }
    }

    private val namesList = listOf(
        "Anthony",
        "Andrew",
        "Alexander",
        "Aaron",
        "Atlas",
        "Adam",
        "Austin",
        "Arthur",
        "Albert",
        "Alan",
        "Asher",
        "Anders",
        "Aiden",
        "Ace",
        "Ashton",
        "Alistair",
        "Alfie",
        "Aidan",
        "Adrian",
        "Archer",
        "Alessio",
        "Arjuna",
        "Abel",
        "Abraham",
        "Alfred",
        "Asim",
        "Alfie",
        "Aman",
        "Archie",
        "Arlo",
        "Ashton",
        "Astor",
        "Alvin",
        "Archibald",
        "Arthur",
        "Alec",
        "Amir",
        "Alejandro",
        "Adonis",
        "Antonio",
        "Andres",
        "Aristotle",
        "Ashley",
        "Akiro",
        "Alberto"
    )
}