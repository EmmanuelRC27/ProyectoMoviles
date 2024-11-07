package com.emma.miniproyecto1

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class GoalListActivity : AppCompatActivity() {

    private lateinit var spinnerGoals: Spinner
    private lateinit var listViewGoals: ListView
    private lateinit var btnEditGoal: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals_list)

        spinnerGoals = findViewById(R.id.spinnerGoals)
        listViewGoals = findViewById(R.id.listViewGoals)
        btnEditGoal = findViewById(R.id.btn_edit_goal)

        val goals = resources.getStringArray(R.array.goals_array)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, goals)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGoals.adapter = adapter

        // Configura el OnClickListener para el botón
        btnEditGoal.setOnClickListener {
            val intent = Intent(this, EditGoalActivity::class.java)
            startActivity(intent)
        }
    }
}
