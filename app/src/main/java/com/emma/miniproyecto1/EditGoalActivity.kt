package com.emma.miniproyecto1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emma.miniproyecto1.entities.GoalEntity
import com.emma.miniproyecto1.Model.GoalRepository

class EditGoalActivity : AppCompatActivity() {

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    private lateinit var imageViewGoal: ImageView
    private var goal: GoalEntity? = null
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_goal_activity)

        imageViewGoal = findViewById(R.id.imageView2)
        val editGoalName: EditText = findViewById(R.id.editTextGoalName)
        val editGoalDescription: EditText = findViewById(R.id.editTextDescription)
        val buttonUploadImage: Button = findViewById(R.id.buttonUploadImage)

        // Cargar datos de la meta seleccionada
        val goalId = intent.getStringExtra("GOAL_ID") ?: "" // Obtener ID como String
        goal = GoalRepository.getGoalById(goalId)

        if (goal != null) {
            editGoalName.setText(goal?.name)
            editGoalDescription.setText(goal?.description)
            goal?.imageUri?.let {
                selectedImageUri = Uri.parse(it)
                imageViewGoal.setImageURI(selectedImageUri)
            }
        } else {
            Toast.makeText(this, "Meta no encontrada", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Botón para seleccionar una imagen
        buttonUploadImage.setOnClickListener {
            openImagePicker()
        }

        // Botón para guardar los cambios
        val buttonEdit: Button = findViewById(R.id.buttonEdit)
        buttonEdit.setOnClickListener {
            goal = goal?.copy(
                name = editGoalName.text.toString(),
                description = editGoalDescription.text.toString(),
                imageUri = selectedImageUri?.toString() ?: "" // Manejar null
            )
            goal?.let { GoalRepository.updateGoal(it) }
            finish()
        }

        // Botón para eliminar la meta
        val buttonDelete: Button = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            goal?.let { GoalRepository.deleteGoal(it) }
            finish()
        }
    }

    // Abrir el selector de imágenes
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Manejar el resultado de la selección de la imagen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            imageViewGoal.setImageURI(selectedImageUri)
        }
    }
}
