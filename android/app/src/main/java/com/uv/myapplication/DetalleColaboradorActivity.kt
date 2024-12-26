package com.uv.myapplication

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import com.uv.myapplication.databinding.ActivityDetalleColaboradorBinding

import com.uv.myapplication.poko.Colaborador
import com.uv.myapplication.utilidades.Constante

class DetalleColaboradorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleColaboradorBinding
    private var colaborador: Colaborador? = null // Objeto que contiene los datos del colaborador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Detalle del colaborador" // Título de la pantalla
        // Inflar el layout y configurar la vista
        binding = ActivityDetalleColaboradorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener los datos del colaborador
        obtenerColaborador()
        // Si se tiene el colaborador, llenar el formulario con sus datos y cargar su foto
        colaborador?.let {
            llenarDetalles(it)
            descargarfoto(it.idColaborador!!)
        }
        binding.btnEditar.setOnClickListener {
            if (colaborador != null) {
                val gson = Gson()
                val stringColaborador = gson.toJson(colaborador)
                val intent = Intent(this@DetalleColaboradorActivity, EditarActiviy::class.java)
                intent.putExtra("Colaborador", stringColaborador)
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay colaborador", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnEnvio.setOnClickListener {
            if (colaborador != null) {
                val gson = Gson()
                val stringColaborador = gson.toJson(colaborador)
                val intent = Intent(this@DetalleColaboradorActivity, MainActivity::class.java)
                intent.putExtra("Colaborador", stringColaborador)
                startActivity(intent)
            }
        }
    }
    private fun llenarDetalles(it: Colaborador) {
        binding.etNombreCompleto.text = "${it.nombre} ${it.apellidoPaterno} ${it.apellidoMaterno}".uppercase()
        binding.etNombre.text = "Nombre : ${it.nombre}"
        binding.etApellidoP.text = "Apellido Paterno: ${it.apellidoPaterno}"
        binding.etApellidoM.text = "Apellido Materno: ${ it.apellidoMaterno }"
        binding.etCurp.text = "CURP: ${it.curp}"
        binding.etEmail.text = "Correo: ${it.correo}"
        binding.etNumeroPersonal.text = "Numero Personal: ${ it.numeroPersonal }"
        binding.etRol.text = "rol: ${it.rol}"
    }

    // Obtener los datos del colaborador desde el Intent
    private fun obtenerColaborador() {
        val gson = Gson()
        val stringColaborador = intent.getStringExtra("Colaborador")
        colaborador = gson.fromJson(stringColaborador, Colaborador::class.java)
    }
    // Descargar la foto del perfil del colaborador
    private fun descargarfoto(idColaborador: Int) {
        binding.progressBar.visibility = View.VISIBLE
        binding.perfil.visibility= View.GONE
        Ion.with(this)
            .load("GET", "${Constante().URL_WS}colaborador/obtenerFoto/$idColaborador")
            .asString()
            .setCallback { e, result ->
                binding.progressBar.visibility = View.GONE
                if (e == null) {
                    visualizarFotoPerfil(result)
                    binding.perfil.visibility= View.VISIBLE

                } else {
                    Log.e("Error", "Error al descargar la foto de perfil: ${e.message}")
                }
            }
    }

    // Mostrar la foto de perfil del colaborador
    private fun visualizarFotoPerfil(json: String) {
        val colaborador = Gson().fromJson(json, Colaborador::class.java)
        if (colaborador?.fotografiaBase64.isNullOrEmpty()) {
            Toast.makeText(this, "No hay foto de perfil", Toast.LENGTH_SHORT).show()
        } else {
            colaborador?.fotografiaBase64?.let { fotografiaBase64 ->
                if (fotografiaBase64.isNotEmpty()) {
                    try {
                        val imageBytes = Base64.decode(fotografiaBase64, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        binding.perfil.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        Log.e("Error", "Error al decodificar la foto de perfil: ${e.message}")
                    }
                }
            }
        }
    }
}
