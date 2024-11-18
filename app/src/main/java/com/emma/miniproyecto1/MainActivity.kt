package com.emma.miniproyecto1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        // Configura el botón "Crear nueva meta"
        val btnCreateGoal: Button = findViewById(R.id.btnMain_CreateGoal)
        btnCreateGoal.setOnClickListener {
            val intent = Intent(this, CreateGoalActivity::class.java)
            startActivity(intent)
        }

        // Configura el botón "Ver metas"
        val btnViewGoals: Button = findViewById(R.id.btnMain_ViewGoals)
        btnViewGoals.setOnClickListener {
            val intent = Intent(this, GoalListActivity::class.java)
            startActivity(intent)
        }
    }
}
