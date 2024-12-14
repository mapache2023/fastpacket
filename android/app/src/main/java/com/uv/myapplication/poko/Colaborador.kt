package com.uv.myapplication.poko

data class Colaborador (
       var contrasena: String,
       var nombre: String?,
       var apellidoPaterno: String,
       var apellidoMaterno: String,
       var correo: String,
       var curp: String,
       val idColaborador: Int?,
       val idRol: Int?,
       val rol: String?,
       val fotografia: ByteArray?,
       val fotografiaBase64:String,
       val numeroLicencia : String,
       val numeroPersonal : String
)