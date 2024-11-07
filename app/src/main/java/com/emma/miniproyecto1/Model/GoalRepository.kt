package com.emma.miniproyecto1.model

import com.emma.miniproyecto1.entities.GoalEntity

interface GoalRepository {
    fun addGoal(goal: GoalEntity)
    fun getGoals(): List<GoalEntity>
    fun updateGoal(goal: GoalEntity)
    fun deleteGoal(goalId: Int)
}
