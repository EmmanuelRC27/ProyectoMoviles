package com.emma.miniproyecto1.model

import com.emma.miniproyecto1.entities.GoalEntity

class ManagerMemory : GoalRepository {
    private val goals = mutableListOf<GoalEntity>() // Lista en memoria para almacenar las metas
    private var nextId = 1 // ID autoincrementable para las metas

    override fun addGoal(goal: GoalEntity) {
        // Asignar un nuevo ID y agregar la meta a la lista
        val newGoal = goal.copy(id = nextId++)
        goals.add(newGoal)
    }

    override fun getGoals(): List<GoalEntity> {
        // Retornar una copia de la lista de metas
        return goals.toList()
    }

    override fun updateGoal(goal: GoalEntity) {
        // Buscar la meta por ID y actualizarla si existe
        val index = goals.indexOfFirst { it.id == goal.id }
        if (index != -1) {
            goals[index] = goal
        }
    }

    override fun deleteGoal(goalId: Int) {
        // Eliminar la meta por ID
        goals.removeIf { it.id == goalId }
    }
}
