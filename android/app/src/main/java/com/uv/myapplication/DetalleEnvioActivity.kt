package com.uv.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import com.uv.myapplication.databinding.ActivityDetalleEnvioBinding
import com.uv.myapplication.poko.Cliente
import com.uv.myapplication.poko.Envio
import com.uv.myapplication.poko.NombreConductor
import com.uv.myapplication.utilidades.Constante

class DetalleEnvioActivity : AppCompatActivity() {

    // Variables para manejar la vista y los datos
    private lateinit var binding: ActivityDetalleEnvioBinding
    private lateinit var envio: Envio // Objeto que contiene la información del envío
    private lateinit var cliente: Cliente // Objeto que contiene la información del cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el layout de la actividad y configurarlo
        binding = ActivityDetalleEnvioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Detalle de envío" // Título de la pantalla
        // Cargar la información del envío desde el Intent
        cargarInformacionEnvio()


    }

    private fun cargarInformacionEnvio() {

        val nombreColaborador = intent.getStringExtra("nombreColaborador")
        if (!nombreColaborador.isNullOrEmpty()) {

        }

        // Obtener el JSON del envío desde el Intent
        val jsonEnvio = intent.getStringExtra("envio")
        val gson = Gson()

        // Convertir el JSON en un objeto Envio
        envio = gson.fromJson(jsonEnvio, Envio::class.java)

        // Mostrar la información del envío en los TextViews de la interfaz
        binding.tvDirrecionOrigen.text = "Direcion de origen: ${envio.origenDireccion}"
        binding.tvDireccionDestino.text = "Direccion de destino: ${envio.destinoDireccion}"
        binding.tvEstatus.text = "Estatus Actual: ${envio.estado}"

        // Consultar la información del cliente asociado al envío
        consultarInformacionCliente(envio.idCliente)

        // Si el envío tiene paquetes, cargar la lista de paquetes
        if (envio.paquetes!!.isNotEmpty()) {
            binding.tvSinPaquetes.visibility = View.GONE
            cargarListaPaquetes()
        } else {
            binding.tvSinPaquetes.visibility = View.VISIBLE
        }
    }

    private fun cargarListaPaquetes() {
        // Configurar el RecyclerView para mostrar la lista de paquetes
        binding.reciclerPaquetes.layoutManager = LinearLayoutManager(this)
        binding.reciclerPaquetes.setHasFixedSize(true)

        // Asignar el adaptador al RecyclerView con la lista de paquetes del envío
        binding.reciclerPaquetes.adapter = PaquetesAdapter(envio.paquetes)
    }

    private fun consultarInformacionCliente(idCliente: Int) {
        // Realizar una solicitud GET para obtener los datos del cliente desde la API
        Ion.with(this@DetalleEnvioActivity)
            .load("GET", "${Constante().URL_WS}enviosEspeciales/app/cliente/${idCliente}")
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    // Si la respuesta es exitosa, procesar la información del cliente
                    serializarInformacion(result)
                } else {
                    // Si hay un error al obtener la información, mostrar un mensaje
                    Toast.makeText(
                        this@DetalleEnvioActivity,
                        "Por el momento no se puede obtener la información del cliente",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun serializarInformacion(result: String) {
        // Convertir el JSON de la respuesta en un objeto Cliente
        val gson = Gson()
        cliente = gson.fromJson(result, Cliente::class.java)

        // Mostrar la información del cliente en los TextViews de la interfaz
        binding.tvNombreCliente.text ="Nombre: ${cliente.nombre}"
        binding.tvTelefono.text = "telefono: ${cliente.telefono}"
        binding.ctvCorreo.text = "Correo: ${cliente.correo}"
    }
}
