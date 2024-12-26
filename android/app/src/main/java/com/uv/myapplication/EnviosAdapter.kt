package com.uv.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.uv.myapplication.interfaz.NotificarClic
import com.uv.myapplication.poko.Envio

// Adapter para manejar los envíos en un RecyclerView
class EnviosAdapter(
    private val envios: ArrayList<Envio>, // Lista de envíos que se mostrarán
    private val observador: NotificarClic, // Interfaz para notificar el clic en un ítem
    private val context: Context,
    private val nombreColaborador: String
) : RecyclerView.Adapter<EnviosAdapter.ViewHolderEnvios>() {

    // ViewHolder que representa cada ítem del RecyclerView
    class ViewHolderEnvios(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvGuia: TextView = itemView.findViewById(R.id.tv_guia) // TextView para mostrar el número de guía
        val tvDireccion: TextView = itemView.findViewById(R.id.tv_direccion) // TextView para mostrar la dirección de destino
        val tvEstatus: TextView = itemView.findViewById(R.id.tv_estatus) // TextView para mostrar el estado del envío
        val cardItem: CardView = itemView.findViewById(R.id.card_item_envio) // CardView que contiene cada ítem del envío
        val btnActualizar: TextView = itemView.findViewById(R.id.btnActualizar) // Botón para actualizar el estado del envío
        val btnVerDetalle: TextView = itemView.findViewById(R.id.btnVerDetalle) // Botón para ver los detalles del envío
    }

    // funcion para crear un nuevo ViewHolder (cuando se necesita crear una nueva vista para un ítem)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderEnvios {
        // Inflar el layout del ítem de la lista
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_envio, parent, false)
        return ViewHolderEnvios(itemView) // Devolver el ViewHolder con la vista inflada
    }

    // funcion que devuelve la cantidad de ítems en la lista
    override fun getItemCount(): Int {
        return envios.size
    }

    // funcion que vincula los datos del envío con las vistas en el ViewHolder
    override fun onBindViewHolder(holder: ViewHolderEnvios, position: Int) {
        val envio = envios[position] // Obtener el envío en la posición correspondiente

        // Asignar los valores a los TextViews
        holder.tvGuia.text = "Numero de Guia: ${envio.numeroGuia}"
        holder.tvDireccion.text = "Direccion de destino: ${envio.destinoDireccion}"


        // Cambiar el color del estado según el valor de 'estado'
       if(!envio.estado.isNullOrEmpty()){
           holder.tvEstatus.text = "Estado: ${envio.estado}"
           when (envio.estado) {
               "Cancelado", "Detenido" -> holder.tvEstatus.setTextColor(Color.RED) // Si está cancelado o detenido, mostrar en rojo
               "Entregado" -> holder.tvEstatus.setTextColor(Color.GREEN) // Si está entregado, mostrar en verde
               else -> holder.tvEstatus.setTextColor(Color.YELLOW) // Para otros estados, mostrar en amarillo
           }
       }


        // Configurar el clic sobre el CardView para notificar la selección del ítem
        holder.cardItem.setOnClickListener {
            observador.seleccionarItem(position, envio) // Notificar al observador sobre el clic
        }
        holder.btnActualizar.setOnClickListener {
            val intent = Intent(context, ActualizarEstado::class.java).apply {
                val gson = Gson()
                val stringEnvio = gson.toJson(envio)
                putExtra("envio", stringEnvio)  // Pasar el objeto 'Envio' como extra.
                putExtra("nombreColaborador", nombreColaborador)
            }
            context.startActivity(intent)

        }
        holder.btnVerDetalle.setOnClickListener {
            val intent = Intent(context, DetalleEnvioActivity::class.java).apply {
                val gson = Gson()
                val stringEnvio = gson.toJson(envio)
                putExtra("envio", stringEnvio)  // Pasar el objeto 'Envio' como extra.
                putExtra("nombreColaborador", nombreColaborador)
            }
            context.startActivity(intent)
        }
    }
}
