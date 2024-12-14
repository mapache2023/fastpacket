package com.uv.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uv.myapplication.poko.Paquete

class PaquetesAdapter(private val paquetes: ArrayList<Paquete>?): RecyclerView.Adapter<PaquetesAdapter.ViewHolderPaquete>() {

    class ViewHolderPaquete(itemView : View)  : RecyclerView.ViewHolder(itemView){
       val tvDescripcion: TextView = itemView.findViewById(R.id.tv_descripcion)
       val tvPeso: TextView = itemView.findViewById(R.id.tv_peso)
       val tvDimensiones: TextView = itemView.findViewById(R.id.tv_dimenciones)


   }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderPaquete {
       val itemView = LayoutInflater.from(parent.context)
           .inflate(R.layout.item_list_paquete, parent, false)
        return ViewHolderPaquete(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderPaquete, position: Int) {
        val paquete = paquetes!![position]
        holder.tvDescripcion.text = "Descripcion de paquete: ${paquete.descripcion}"
        holder.tvPeso.text = "Peso: ${paquete.peso}kg"
        holder.tvDimensiones.text = "dimenciones:  ${paquete.ancho}cm x ${paquete.alto}cm x ${paquete.profundidad}cm"

    }

    override fun getItemCount(): Int {
    return paquetes!!.size
   }
}