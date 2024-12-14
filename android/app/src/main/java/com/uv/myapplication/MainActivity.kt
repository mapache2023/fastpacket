package com.uv.myapplication

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.uv.myapplication.databinding.ActivityMainBinding
import com.uv.myapplication.interfaz.NotificarClic
import com.uv.myapplication.poko.Colaborador
import com.uv.myapplication.poko.Envio
import com.koushikdutta.ion.Ion
import com.uv.myapplication.poko.Paquete
import com.uv.myapplication.utilidades.Constante
import android.content.Intent

class MainActivity : AppCompatActivity(), NotificarClic {

    private lateinit var binding: ActivityMainBinding
    private lateinit var colaborador: Colaborador
    private var listaEnvio: ArrayList<Envio> = ArrayList()
    private lateinit var nombreCompleto: String
    // Implementación del funcion 'seleccionarItem' para Paquete, pero no se utiliza en esta actividad.
    override fun seleccionarItem(posicion: Int, paquete: Paquete) {
        // Este funcion está vacío, ya que no se requiere en esta clase, puede eliminarse si no es necesario.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "FastPacket"  // Título de la pantalla principal

        // Obtener los datos del colaborador desde el Intent.
        obtenerDatosColaborador()

        // Si el colaborador es válido, consultar información de los envíos.
        if (::colaborador.isInitialized) {
            consultarInformacionEnvio(colaborador.idColaborador)
            nombreCompleto = "${colaborador.nombre} ${colaborador.apellidoPaterno} ${colaborador.apellidoMaterno}"
            binding.tvConductor.text = nombreCompleto.uppercase()
        }

        // Acción del botón para editar el colaborador.
        binding.tvConductor.setOnClickListener {
            val gson = Gson()
            val stringColaborador = gson.toJson(colaborador)
            val intent = Intent(this@MainActivity, DetalleColaboradorActivity::class.java)
            intent.putExtra("Colaborador", stringColaborador)
            startActivity(intent)
        }
        binding.btnCerrarSesion.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnRecargarLista.setOnClickListener {
            consultarInformacionEnvio(colaborador.idColaborador)
        }
    }

    // Consultar los envíos del colaborador a través de la API.
    private fun consultarInformacionEnvio(idColaborador: Int?) {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerEnvios.visibility= View.GONE
        Ion.with(this@MainActivity)
            .load("GET", "${Constante().URL_WS}enviosEspeciales/app/conductor/$idColaborador")
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    binding.progressBar.visibility= View.GONE

                    serializarInformacionEnvio(result)  // Procesar la información de los envíos.
                    cargarInformacionRecycler()  // Cargar los envíos en el RecyclerView.
                    binding.recyclerEnvios.visibility= View.VISIBLE

                } else {
                    Toast.makeText(this@MainActivity, "No se puede obtener la información de los envíos.", Toast.LENGTH_LONG).show()
                }
            }
    }

    // Obtener los datos del colaborador del Intent.
    private fun obtenerDatosColaborador() {
        val jsonColaborador = intent.getStringExtra("Colaborador")
        val gson = Gson()
        if (jsonColaborador != null) {
            colaborador = gson.fromJson(jsonColaborador, Colaborador::class.java)
        }
    }

    // Configurar y mostrar los envíos en el RecyclerView.
    private fun cargarInformacionRecycler() {
        binding.recyclerEnvios.layoutManager = LinearLayoutManager(this)
        binding.recyclerEnvios.setHasFixedSize(true)

        // Verificar si hay envíos para mostrar.
        if (listaEnvio.isNotEmpty()) {
            binding.tvSinEnvios.visibility = View.GONE
            binding.recyclerEnvios.adapter = EnviosAdapter(listaEnvio, this,this,nombreCompleto)
        } else {
            binding.tvSinEnvios.visibility = View.VISIBLE
        }
    }

    // Convertir la respuesta JSON de los envíos en objetos 'Envio'.
    private fun serializarInformacionEnvio(json: String) {
        val gson = Gson()
        val typeLista = object : TypeToken<ArrayList<Envio>>() {}.type
        listaEnvio = gson.fromJson(json, typeLista)
    }

    // Implementación del funcion 'seleccionarItem' para manejar el clic en un envío.
    override fun seleccionarItem(posicion: Int, envio: Envio) {
        Toast.makeText(this@MainActivity, "Posición seleccionada: $posicion", Toast.LENGTH_SHORT).show()
    }


}
