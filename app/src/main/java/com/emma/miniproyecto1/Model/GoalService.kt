package com.emma.miniproyecto1.model // Ajusta esto según la ubicación del archivo

import com.emma.miniproyecto1.entities.GoalClassification
import com.emma.miniproyecto1.entities.GoalEntity
import com.emma.miniproyecto1.Model.GoalRepository

object GoalService {

    // Crear una meta
    fun createGoal(
        name: String,
        description: String,
        imageUri: String?,
        classification: GoalClassification
    ) {
        val goal = GoalEntity(
            id = 0, // El ID será asignado automáticamente en el repositorio
            name = name,
            description = description,
            imageUri = imageUri,
            classification = classification
        )
        GoalRepository.insertGoal(goal)
    }

    // Obtener todas las metas
    fun getAllGoals(): List<GoalEntity> {
        return GoalRepository.getGoals()
    }

    // Obtener una meta por ID
    fun getGoalById(id: Int): GoalEntity? {
        return GoalRepository.getGoals().find { it.id == id }
    }

    // Actualizar una meta
    fun updateGoal(goal: GoalEntity) {
        GoalRepository.updateGoal(goal)
    }

    // Eliminar una meta
    fun deleteGoal(goal: GoalEntity) {
        GoalRepository.deleteGoal(goal)
    }
}
