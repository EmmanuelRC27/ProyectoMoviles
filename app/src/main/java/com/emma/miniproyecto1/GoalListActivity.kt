package com.emma.miniproyecto1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emma.miniproyecto1.Model.GoalRepository // Importa correctamente el paquete
import com.emma.miniproyecto1.entities.GoalEntity

class GoalListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GoalsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals_list)

        recyclerView = findViewById(R.id.recyclerViewGoals)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Cargar metas en el RecyclerView
        loadGoals()
    }

    private fun loadGoals() {
        // Obtener metas directamente desde el repositorio
        val goals: List<GoalEntity> = GoalRepository.getGoals()

        // Configurar el adaptador con las metas cargadas
        adapter = GoalsAdapter(goals)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        // Actualizar la lista de metas cada vez que regreses a esta actividad
        loadGoals()
    }
}
