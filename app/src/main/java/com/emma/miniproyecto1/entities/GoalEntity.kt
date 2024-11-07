package com.emma.miniproyecto1.entities

enum class GoalClassification {
    SHORT_TERM,
    MEDIUM_TERM,
    LONG_TERM
}



data class GoalEntity(
    val id: Int,                      // Unique identifier
    var title: String,                // Title of the goal
    var description: String,          // Description of the goal
    var startDate: String,            // Start date
    var endDate: String,              // Due date
    var classification: GoalClassification,  // Classification
    var imagePath: String?            // Local path of the motivational image (optional)
)
