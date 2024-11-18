package com.emma.miniproyecto1.Model

import com.emma.miniproyecto1.entities.GoalEntity
import java.util.UUID

object GoalRepository {
    private val goals = mutableListOf<GoalEntity>()

    // Insertar una nueva meta
    fun insertGoal(goal: GoalEntity) {
        val newGoal = goal.copy(id = UUID.randomUUID().toString())
        goals.add(newGoal)
    }

    // Obtener todas las metas
    fun getGoals(): List<GoalEntity> = goals.toList()

    // Obtener una meta por ID
    fun getGoalById(id: String): GoalEntity? {
        return goals.find { it.id == id } // Busca por ID en la lista
    }

    // Actualizar una meta
    fun updateGoal(updatedGoal: GoalEntity) {
        val index = goals.indexOfFirst { it.id == updatedGoal.id }
        if (index != -1) {
            goals[index] = updatedGoal
        }
    }

    // Eliminar una meta
    fun deleteGoal(goal: GoalEntity) {
        goals.removeIf { it.id == goal.id }
    }
}
