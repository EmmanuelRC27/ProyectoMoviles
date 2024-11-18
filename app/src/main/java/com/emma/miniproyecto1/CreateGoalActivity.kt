package com.emma.miniproyecto1

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.emma.miniproyecto1.entities.GoalClassification
import com.emma.miniproyecto1.entities.GoalEntity
import com.emma.miniproyecto1.Model.GoalRepository
import java.io.File
import java.io.FileOutputStream
import java.util.*

class CreateGoalActivity : AppCompatActivity() {

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val TAKE_PHOTO_REQUEST = 2
    }

    private lateinit var imageViewGoal: ImageView
    private lateinit var spinnerGoalCategory: Spinner
    private var selectedImageUri: Uri? = null
    private var selectedDate: String = "Fecha no seleccionada"
    private var selectedClassification: GoalClassification = GoalClassification.SHORT_TERM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_goal)

        val editGoalName: EditText = findViewById(R.id.editTextGoalName)
        val editGoalDescription: EditText = findViewById(R.id.editTextGoalDescription)
        val btnSelectDate: Button = findViewById(R.id.btnSelectDate)
        val buttonUploadImage: Button = findViewById(R.id.buttonUploadImage)
        val buttonSaveGoal: Button = findViewById(R.id.buttonSaveGoal)
        imageViewGoal = findViewById(R.id.imageViewGoal)
        spinnerGoalCategory = findViewById(R.id.spinnerGoalCategory)

        // Configurar el Spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.goal_categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerGoalCategory.adapter = adapter
        }

        btnSelectDate.setOnClickListener {
            showDatePicker { date ->
                selectedDate = date
                findViewById<TextView>(R.id.tvSelectedDate).text = date
            }
        }

        buttonUploadImage.setOnClickListener {
            showImagePickerDialog()
        }

        buttonSaveGoal.setOnClickListener {
            val name = editGoalName.text.toString()
            val description = editGoalDescription.text.toString()

            if (name.isEmpty() || description.isEmpty() || selectedDate == "Fecha no seleccionada") {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val goal = GoalEntity(
                id = UUID.randomUUID().toString(),
                name = name,
                description = "$description\nFecha límite: $selectedDate",
                imageUri = selectedImageUri?.toString() ?: "", // Manejo de null
                classification = selectedClassification
            )
            GoalRepository.insertGoal(goal)



            GoalRepository.insertGoal(goal)
            Toast.makeText(this, "Meta guardada exitosamente", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDateSelected(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Seleccionar de la Galería", "Tomar Foto")
        AlertDialog.Builder(this)
            .setTitle("Selecciona una opción")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openGallery()
                    1 -> takePhoto()
                }
            }
            .show()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun takePhoto() {
        val photoFile = File.createTempFile(
            "IMG_${System.currentTimeMillis()}",
            ".jpg",
            getExternalFilesDir(null)
        )
        selectedImageUri = FileProvider.getUriForFile(
            this,
            "${packageName}.fileprovider",
            photoFile
        )

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, selectedImageUri)
        }
        startActivityForResult(intent, TAKE_PHOTO_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    selectedImageUri = data?.data
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                    selectedImageUri = saveImageToInternalStorage(bitmap, "goal_${System.currentTimeMillis()}.jpg")
                    imageViewGoal.setImageURI(selectedImageUri)
                }
                TAKE_PHOTO_REQUEST -> {
                    imageViewGoal.setImageURI(selectedImageUri)
                }
            }
        }
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap, fileName: String): Uri {
        val file = File(filesDir, fileName)
        FileOutputStream(file).use { fos ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        }
        return Uri.fromFile(file)
    }
}
