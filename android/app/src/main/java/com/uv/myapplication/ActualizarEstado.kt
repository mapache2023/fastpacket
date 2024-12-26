package com.uv.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import com.uv.myapplication.databinding.ActivityActualizarEstadoBinding
import com.uv.myapplication.poko.Envio
import com.uv.myapplication.poko.Historial
import com.uv.myapplication.poko.Mensaje
import com.uv.myapplication.poko.NombreConductor
import com.uv.myapplication.utilidades.Constante

class ActualizarEstado : AppCompatActivity() {

    private lateinit var envio: Envio // Objeto que contiene la información del envío
    private lateinit var binding: ActivityActualizarEstadoBinding // Enlace con los elementos de la interfaz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Cambiar estado" // Título de la pantalla
        // Obtener el envio actual desde el Intent
        val envioJson = intent.getStringExtra("envio") // El estado del envío se pasa como un JSON
        Log.d("JSONeNVIO", envioJson.toString())


        envio = Gson().fromJson(envioJson, Envio::class.java) // Convertir el JSON a objeto Envio

        // Inflar el layout y asignarlo a la actividad
        binding = ActivityActualizarEstadoBinding.inflate(layoutInflater)
        setContentView(binding.root) // Establecer la vista de la actividad
        val nombreColaborador = intent.getStringExtra("nombreColaborador")
        if (!nombreColaborador.isNullOrEmpty()) {

        }
        // Mostrar el estado actual del envío en la interfaz
        binding.tvEstado.text = "Estado actual: "

        // Configurar el Spinner con los diferentes estados posibles
        val estados = resources.getStringArray(R.array.estados) // Array con los estados disponibles
        binding.spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estados)
        estados.indexOf(envio.estado).takeIf { it >= 0 }?.let {
            binding.spinner.setSelection(it)
        }
        // Listener para manejar cuando el usuario selecciona un nuevo estado
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val nuevoEstado = parent?.getItemAtPosition(position).toString()
                envio.estado = nuevoEstado // Actualizar el estado del objeto Envio

                // Si el estado es "Detenido" o "Cancelado", mostrar el campo de comentario
                binding.elComentario.visibility = if (nuevoEstado == "Detenido" || nuevoEstado == "Cancelado") {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hacer nada si no se selecciona ningún estado
            }
        }

        // Configurar el botón para actualizar el estado
        binding.btnActualizar.setOnClickListener {
            actualizarEstado() // Llamar al funcion para actualizar el estado

        }
    }

    private fun actualizarEstado() {
        if(envio.estado.equals("Detenido") || envio.estado.equals("Cancelado")){
            if(binding.etComentario.text.toString().isEmpty()){
                Toast.makeText(this@ActualizarEstado, "Por favor ingrese un comentario", Toast.LENGTH_LONG).show()
                return
            }
        }
        val comentario = binding.etComentario.text.toString() // Obtener el comentario ingresado
        val idEstado = obtenerIdEstado(envio.estado) // Obtener el ID correspondiente al estado seleccionado

        // Crear un objeto Historial con la información para guardar
        val historial = Historial(comentario, envio.idEnvio, envio.idColaborador, idEstado)
        val jsonHistorial = Gson().toJson(historial) // Convertir el objeto Historial a JSON
        Log.d("JSON", jsonHistorial)

        // Enviar la solicitud para actualizar el estado
        enviarHistorial(jsonHistorial)
    }

    private fun obtenerIdEstado(estado: String): Int {
        // Asignar un ID numérico basado en el estado seleccionado
        return when (estado) {
            "Detenido" -> 3
            "Cancelado" -> 4
            "Entregado" -> 5
            "En transito" -> 2
            "Pendiente" -> 1
            else -> 0 // En caso de un estado no esperado
        }
    }

    private fun enviarHistorial(jsonHistorial: String) {
        binding.progressBar.visibility = View.VISIBLE // Mostrar la barra de progreso
        // Enviar la solicitud para actualizar el estado del envío
        Ion.with(this@ActualizarEstado)
            .load("POST", "${Constante().URL_WS}enviosEspeciales/app/cambios") // URL del servicio
            .setHeader("Content-Type", "application/json") // Establecer el tipo de contenido
            .setStringBody(jsonHistorial) // Agregar el cuerpo de la solicitud (el JSON)
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                Log.d("Respuesta", result)
                verificarResultadoEdicion(result) // Si la respuesta es exitosa, verificar el resultado
                    binding.progressBar.visibility = View.GONE // Ocultar la barra de progreso
                } else {
                    // Si ocurre un error en la solicitud, mostrar un mensaje
                    Toast.makeText(this@ActualizarEstado, "Error al actualizar la información.", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun verificarResultadoEdicion(resultado: String) {
        // Verificar el resultado de la edición del estado
        val msj = Gson().fromJson(resultado, Mensaje::class.java) // Convertir la respuesta a un objeto Mensaje
        Toast.makeText(this@ActualizarEstado, msj.mensaje, Toast.LENGTH_LONG).show() // Mostrar el mensaje

        // Si no hubo error, mostrar un mensaje adicional
        if (!msj.error) {
            Toast.makeText(this@ActualizarEstado, "Estado actualizado correctamente.", Toast.LENGTH_LONG).show()
            finish() // Cerrar la actividad
        }
        else{
            Toast.makeText(this@ActualizarEstado, "Error al actualizar la información.", Toast.LENGTH_LONG).show()
        }
    }
}
