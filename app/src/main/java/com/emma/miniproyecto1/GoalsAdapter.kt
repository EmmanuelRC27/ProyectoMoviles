package com.emma.miniproyecto1

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emma.miniproyecto1.entities.GoalEntity

class GoalsAdapter(private val goals: List<GoalEntity>) : RecyclerView.Adapter<GoalsAdapter.GoalViewHolder>() {

    inner class GoalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.textGoalName)
        val description: TextView = view.findViewById(R.id.textGoalDescription)
        val image: ImageView = view.findViewById(R.id.imageGoal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_goal, parent, false)
        return GoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = goals[position]
        holder.name.text = goal.name
        holder.description.text = goal.description

        val uri = Uri.parse(goal.imageUri ?: "") // Maneja posibles valores null
        Glide.with(holder.image.context)
            .load(uri)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.imagen_principal)
            .into(holder.image)
    }


    override fun getItemCount(): Int = goals.size
}
