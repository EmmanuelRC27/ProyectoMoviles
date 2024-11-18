package com.emma.miniproyecto1.model

import com.emma.miniproyecto1.entities.GoalEntity
import com.emma.miniproyecto1.Model.GoalRepository

class MemoryManager {

    private val goalRepository = GoalRepository // Accede directamente al singleton

    fun addGoal(goal: GoalEntity) {
        goalRepository.insertGoal(goal)
    }

    fun getGoals(): List<GoalEntity> {
        return goalRepository.getGoals()
    }

    fun updateGoal(goal: GoalEntity) {
        goalRepository.updateGoal(goal)
    }

    fun deleteGoal(goal: GoalEntity) {
        goalRepository.deleteGoal(goal)
    }
}
