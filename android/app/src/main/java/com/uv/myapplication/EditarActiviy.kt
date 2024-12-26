package com.uv.myapplication

import android.R
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import com.uv.myapplication.databinding.ActivityEditarActiviyBinding
import com.uv.myapplication.poko.Colaborador
import com.uv.myapplication.utilidades.Constante
import android.widget.Toast
import com.uv.myapplication.poko.Mensaje
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.uv.myapplication.poko.NombreConductor
import java.io.ByteArrayOutputStream

class EditarActiviy : AppCompatActivity() {
    private var fotoPerfilByte: ByteArray? = null // Variable para almacenar la foto del perfil en formato byte[]
    private lateinit var binding: ActivityEditarActiviyBinding
    private var colaborador: Colaborador? = null // Objeto que contiene los datos del colaborador
    private val roles = listOf("Conductor", "Administrador", "Ejecutivo de tienda") // Roles disponibles

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Formulario de edicion" // Título de la pantalla
        // Inflar el layout y configurar la vista
        binding = ActivityEditarActiviyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener los datos del colaborador
        obtenerColaborador()

        // Si se tiene el colaborador, llenar el formulario con sus datos y cargar su foto
        colaborador?.let {
            llenarFormulario(it)
            descargarfoto(it.idColaborador!!)
        }

        // Desactivar la edición de los campos de "Número Personal" y "Rol"
        binding.etNumeroPersonal.isEnabled = false
        binding.layoutNumeroPersonal.isEnabled = false
        binding.spRol.isEnabled = false

        // Configurar el Spinner con los roles disponibles
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, roles)
        binding.spRol.adapter = adapter

        // Acción al hacer clic en el botón para subir una nueva foto
        binding.btnSubirFoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            seleccionarFoto.launch(intent)
        }

        // Acción al hacer clic en el botón para guardar los cambios
        binding.btnEditar.setOnClickListener {
            if (validarCampos()) {
                colaborador!!.nombre = binding.etNombre.text.toString()
                colaborador!!.apellidoPaterno = binding.etApellidoP.text.toString()
                colaborador!!.apellidoMaterno = binding.etApellidoM.text.toString()
                colaborador!!.curp = binding.etCurp.text.toString()
                colaborador!!.correo = binding.etEmail.text.toString()
                colaborador!!.contrasena = binding.etContrasena.text.toString()
                colaborador!!.numeroLicencia = binding.etNumeroLicencia.text.toString()
                actualizarInformacion(colaborador!!)
            }

        }
    }

    // funcion para validar que los campos del formulario estén completos
    private fun validarCampos(): Boolean {
        var camposValidos = true
        // Validación de cada campo requerido
        if (binding.etNombre.text.toString().isEmpty()) {
            binding.etNombre.error = "Campo requerido"
            camposValidos = false
        }
        if (binding.etApellidoP.text.toString().isEmpty()) {
            binding.etApellidoP.error = "Campo requerido"
            camposValidos = false
        }
        if (binding.etApellidoM.text.toString().isEmpty()) {
            binding.etApellidoM.error = "Campo requerido"
            camposValidos = false
        }
        if (binding.etCurp.text.toString().isEmpty()) {
            binding.etCurp.error = "Campo requerido"
            camposValidos = false
        }
        if (binding.etEmail.text.toString().isEmpty()) {
            binding.etEmail.error = "Campo requerido"
            camposValidos = false
        }
        if (binding.etContrasena.text.toString().isEmpty()) {
            binding.etContrasena.error = "Campo requerido"
            camposValidos = false
        }

        if(binding.etNumeroLicencia.text.toString().isEmpty()){
            binding.etNumeroLicencia.error = "Campo requerido"
            camposValidos = false
        }
        return camposValidos
    }

    // funcion para llenar el formulario con los datos del colaborador
    private fun llenarFormulario(colaborador: Colaborador) {
        binding.etNombre.setText(colaborador.nombre)
        binding.etApellidoP.setText(colaborador.apellidoPaterno)
        binding.etApellidoM.setText(colaborador.apellidoMaterno)
        binding.etCurp.setText(colaborador.curp)
        binding.etEmail.setText(colaborador.correo)
        binding.etContrasena.setText(colaborador.contrasena)
        binding.etNumeroPersonal.setText(colaborador.numeroPersonal)
        binding.etNumeroLicencia.setText(colaborador.numeroLicencia)

        // Establecer el rol en el Spinner
        roles.indexOf(colaborador.rol).takeIf { it >= 0 }?.let {
            binding.spRol.setSelection(it)
        }
    }

    // funcion para enviar la solicitud de actualización de la información del colaborador
    private fun actualizarInformacion(colaborador: Colaborador) {
        val gson = Gson()
        val json = gson.toJson(colaborador)
        Ion.with(this@EditarActiviy)
            .load("PUT", "${Constante().URL_WS}colaborador/editar")
            .setHeader("Content-Type", "application/json")
            .setStringBody(json)
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    verificarResultadoEdicion(result)
                } else {
                    // Mostrar un mensaje de error si la actualización falla
                    Toast.makeText(this@EditarActiviy, "Error en la petición para actualizar la información.", Toast.LENGTH_LONG).show()
                    Log.e("Error", e?.message.toString())
                }
            }
    }

    // Verificar si la actualización de los datos fue exitosa
    private fun verificarResultadoEdicion(result: String) {
        val gson = Gson()
        val msj: Mensaje = gson.fromJson(result, Mensaje::class.java)
        Toast.makeText(this@EditarActiviy, msj.mensaje, Toast.LENGTH_LONG).show()
        if (!msj.error) {
            NombreConductor.usuario = {"${colaborador!!.nombre} ${colaborador!!.apellidoPaterno} ${colaborador!!.apellidoMaterno}"}.toString()
            val gson = Gson()
            val stringColaborador = gson.toJson(colaborador)
            val intent = Intent(this@EditarActiviy, DetalleColaboradorActivity::class.java)
            intent.putExtra("Colaborador", stringColaborador)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this@EditarActiviy, "Error en la petición para actualizar la información.", Toast.LENGTH_LONG).show()
        }
    }

    // Obtener los datos del colaborador desde el Intent
    private fun obtenerColaborador() {
        val gson = Gson()
        val stringColaborador = intent.getStringExtra("Colaborador")
        colaborador = gson.fromJson(stringColaborador, Colaborador::class.java)
    }

    // Descargar la foto del perfil del colaborador
    // Descargar la foto del perfil del colaborador
    private fun descargarfoto(idColaborador: Int) {
        binding.progressBar.visibility = View.VISIBLE
        binding.imgPerfil.visibility= View.GONE
        Ion.with(this)
            .load("GET", "${Constante().URL_WS}colaborador/obtenerFoto/$idColaborador")
            .asString()
            .setCallback { e, result ->
                binding.progressBar.visibility = View.GONE
                if (e == null) {
                    visualizarFotoPerfil(result)
                    binding.imgPerfil.visibility= View.VISIBLE
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
                        binding.imgPerfil.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        Log.e("Error", "Error al decodificar la foto de perfil: ${e.message}")
                    }
                }
            }
        }
    }

    // Actividad para seleccionar la foto desde la galería
    private val seleccionarFoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let { uri ->
                    fotoPerfilByte = uriToByteArray(uri)
                    fotoPerfilByte?.let { byteArray ->
                        subirfotodeperfil(colaborador!!.idColaborador, byteArray)
                    }
                }
            }
        }

    // Convertir la URI de la imagen seleccionada en un arreglo de bytes
    private fun uriToByteArray(uri: Uri): ByteArray? {
        return try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                val bitmap = BitmapFactory.decodeStream(inputStream)
                ByteArrayOutputStream().apply {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
                }.toByteArray()
            }
        } catch (e: Exception) {
            Log.e("Error", "Error al convertir URI a ByteArray: ${e.message}")
            null
        }
    }

    // Subir la nueva foto de perfil del colaborador
    private fun subirfotodeperfil(idColaborador: Int?, fotoPerfil: ByteArray) {
        Ion.with(this).load("PUT", "${Constante().URL_WS}colaborador/subirFoto/$idColaborador")
            .setByteArrayBody(fotoPerfil).asString()
            .setCallback { e, result ->
                if (e == null) {
                    val mensaje = Gson().fromJson(result, Mensaje::class.java)
                    if (!mensaje.error) {
                        Log.d("Mensaje", mensaje.mensaje)
                        descargarfoto(idColaborador!!) // Descargar la foto actualizada
                    }
                } else {
                    Log.e("Error", "Error al subir la foto de perfil: ${e.message}")
                }
            }
    }
}
