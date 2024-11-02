package com.emma.miniproyecto1.model

import com.emma.miniproyecto1.entities.GoalEntity

class GoalService(private val repository: GoalRepository) {
    fun createGoal(goal: GoalEntity) {
        repository.addGoal(goal)
    }

    fun listGoals(): List<GoalEntity> {
        return repository.getGoals()
    }

    fun editGoal(goal: GoalEntity) {
        repository.updateGoal(goal)
    }

    fun removeGoal(goalId: Int) {
        repository.deleteGoal(goalId)
    }
}
