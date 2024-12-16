package com.uv.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import com.uv.myapplication.databinding.ActivityLoginBinding
import com.uv.myapplication.poko.Colaborador
import com.uv.myapplication.poko.RespuestaLogin
import com.uv.myapplication.utilidades.Constante

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        title = "Inicio de sesión" // Título de la pantalla

        // Configurar el botón de ingreso
        binding.btnIngresar.setOnClickListener {
            // Validar que los campos no estén vacíos antes de verificar las credenciales
            if (validarCamposLogin()) {
                verificarCredencialesColaborador(
                    binding.etNumeroPersonal.text.toString(),
                    binding.etContrasena.text.toString()
                )
            }
        }
    }

    // Validar que los campos de login no estén vacíos
    private fun validarCamposLogin(): Boolean {
        var esCorrecto = true
        val numeroPersonal = binding.etNumeroPersonal.text.toString()
        val contrasena = binding.etContrasena.text.toString()

        // Verificar que el número personal no esté vacío
        if (numeroPersonal.isEmpty()) {
            esCorrecto = false
            binding.etNumeroPersonal.error = "Número personal obligatorio"
        }

        // Verificar que la contraseña no esté vacía
        if (contrasena.isEmpty()) {
            esCorrecto = false
            binding.etContrasena.error = "Contraseña obligatoria"
        }

        return esCorrecto
    }

    // Verificar las credenciales del colaborador mediante una solicitud HTTP POST
    private fun verificarCredencialesColaborador(numeroPersonal: String, contrasena: String) {
        binding.progressBar.visibility = View.VISIBLE
        // Deshabilitar el middleware conscrypt (para permitir conexiones HTTP inseguras)
        Ion.getDefault(this@LoginActivity).conscryptMiddleware.enable(false)

        Ion.with(this@LoginActivity)
            .load("POST", Constante().URL_WS + "login") // URL de la API para login
            .setHeader("Content-Type", "application/x-www-form-urlencoded") // Header para el cuerpo de la solicitud
            .setBodyParameter("numeroPersonal", numeroPersonal) // Parámetro del número personal
            .setBodyParameter("contrasena", contrasena) // Parámetro de la contraseña
            .asString()
            .setCallback { e, result ->
                binding.progressBar.visibility = View.GONE
                if (e == null && result != null) {
                    serializarRespuestaLogin(result) // Si la solicitud es exitosa, procesamos la respuesta
                } else {
                    // Manejo de error en la solicitud
                    Toast.makeText(this@LoginActivity, "Error de solicitud: ${e?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    // Procesar la respuesta de la API después de intentar hacer login
    private fun serializarRespuestaLogin(json: String) {
        val gson = Gson()
        val respuestaLogin = gson.fromJson(json, RespuestaLogin::class.java)

        // Si la respuesta no contiene errores
        if (!respuestaLogin.error) {
            // Verificar si el colaborador tiene el rol adecuado (en este caso, rol 3 para conductor)
            if (respuestaLogin.colaborador!!.idRol == 3) {
                irPantallaPrincipal(respuestaLogin.colaborador!!) // Redirigir a la pantalla principal si es conductor
            } else {
                // Si el colaborador no es conductor, mostrar mensaje
                Toast.makeText(this@LoginActivity, "Actualmente no es conductor. Contacte con administración.", Toast.LENGTH_LONG).show()
            }
        } else {
            // Si la respuesta tiene un error (credenciales incorrectas o problema en el servidor)
            Toast.makeText(this@LoginActivity, "Credenciales incorrectas o error en el servidor", Toast.LENGTH_LONG).show()
        }
    }

    // Redirigir a la pantalla principal si las credenciales son correctas y el rol es adecuado
    private fun irPantallaPrincipal(colaborador: Colaborador) {
        // Serializar el objeto Colaborador a JSON
        val gson = Gson()
        val stringColaborador = gson.toJson(colaborador)

        // Crear un intent para iniciar la actividad principal (MainActivity)
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.putExtra("Colaborador", stringColaborador) // Pasar el objeto Colaborador como extra

        // Iniciar la actividad y finalizar la actual
        startActivity(intent)
        finish() // Cerrar la actividad de login para que no sea accesible desde el botón "Atrás"
    }
}
