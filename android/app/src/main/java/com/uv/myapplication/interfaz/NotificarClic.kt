package com.uv.myapplication.interfaz

import com.uv.myapplication.poko.Envio
import com.uv.myapplication.poko.Paquete

interface NotificarClic {

    fun seleccionarItem(posicion : Int, envio: Envio)
    fun seleccionarItem(posicion : Int, paquete: Paquete)

}