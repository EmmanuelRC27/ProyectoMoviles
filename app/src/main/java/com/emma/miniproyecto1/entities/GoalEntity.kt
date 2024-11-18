package com.emma.miniproyecto1.entities

enum class GoalClassification {
    SHORT_TERM,
    MEDIUM_TERM,
    LONG_TERM
}

data class GoalEntity(
    val id: String,
    val name: String,
    val description: String,
    val imageUri: String, // Asegúrate de que no sea nullable
    val classification: GoalClassification
)
