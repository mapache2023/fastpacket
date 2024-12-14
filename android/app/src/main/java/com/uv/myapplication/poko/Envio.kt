package com.uv.myapplication.poko


 data class Envio(
     val costo :String?,
     val destinoDireccion :String?,
     var estado :String,
     val idCliente :Int,
     val idEnvio :Int,
     val idColaborador :Int,
     val numeroGuia :String?,
     val origenDireccion :String?,
     val paquetes: ArrayList<Paquete>?
)