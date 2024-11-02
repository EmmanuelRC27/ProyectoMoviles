package com.emma.miniproyecto1

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.emma.miniproyecto1.entities.MetaEntity
import com.emma.miniproyecto1.entities.GoalClassification

class MainActivity : AppCompatActivity() {

    private lateinit var goalEntity: MetaEntity

    companion object {
        const val PICK_IMAGE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity) // Asegúrate de que este sea el nombre correcto de tu layout

        // Inicializa goalEntity aquí o cuando sea necesario
        goalEntity = MetaEntity(1, "", "", "", "", GoalClassification.SHORT_TERM, null)

        val btnCreateGoal: Button = findViewById(R.id.btnMain_CreateGoal)
        btnCreateGoal.setOnClickListener {
            checkPermissions() // Verifica permisos antes de crear la meta
        }

        // Configurar el botón "Ver Metas"
        val btnViewGoals: Button = findViewById(R.id.btnMain_ViewGoals)
        btnViewGoals.setOnClickListener {
            // Inicia la actividad GoalListActivity
            val intent = Intent(this, GoalListActivity::class.java)
            startActivity(intent)
        }
    }

    // Método para abrir el selector de imágenes
    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE)
    }

    // Manejar el resultado de la actividad de selección de imágenes
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data // Esta es la URI de la imagen seleccionada

            // Convierte la URI a una ruta y guárdala en la entidad
            selectedImage?.let {
                val imagePath = getRealPathFromURI(it)
                goalEntity = goalEntity.copy(imagePath = imagePath)  // Guardar la ruta en MetaEntity
            }
        }
    }

    // Método para convertir la URI a una ruta real
    private fun getRealPathFromURI(uri: Uri): String? {
        var path: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            path = it.getString(columnIndex)
        }
        return path
    }

    // Método para verificar permisos
    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            selectImage() // Llama a selectImage si ya tienes permisos
        }
    }

    // Manejar la respuesta de la solicitud de permisos
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectImage() // Abre el selector de imágenes si se concede el permiso
        }
    }
}
