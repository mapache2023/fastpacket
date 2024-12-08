package desktop.modelo.dao;

import com.google.gson.Gson;
import desktop.modelo.ConexionWs;
import desktop.modelo.RespuestaHTTP;
import desktop.modelo.pojo.LoginColaborador;
import desktop.utilidades.Constantes;
import java.net.HttpURLConnection;

/**
 * Clase que maneja las operaciones de inicio de sesión para los colaboradores.
 */
public class LoginDAO {

    /**
     * Realiza la solicitud de inicio de sesión utilizando el número de personal y la contraseña.
     *
     * @param noPersonal Número de personal del colaborador.
     * @param password   Contraseña del colaborador.
     * @return Un objeto LoginColaborador con la respuesta del servicio.
     */
    public static LoginColaborador iniciaSesion(String noPersonal, String password) {
        // Objeto para almacenar la respuesta del servicio
        LoginColaborador respuestaLogin = new LoginColaborador();

        // URL del servicio y parámetros para la petición
        String url = Constantes.URI_WS + "login";
        String parametros = String.format("numeroPersonal=%s&contrasena=%s", noPersonal, password);

        // Realizamos la conexión POST
        RespuestaHTTP respuestaConexion = ConexionWs.peticionPOST(url, parametros);

        // Comprobamos si la respuesta es exitosa
        if (respuestaConexion.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            // Si la respuesta es OK, parseamos el JSON al objeto LoginColaborador
            Gson gson = new Gson();
            respuestaLogin = gson.fromJson(respuestaConexion.getContenido(), LoginColaborador.class);
        } else {
            // Si hubo error en la petición, seteamos el objeto con error
            respuestaLogin.setError(true);
            respuestaLogin.setMensaje("Error al realizar la petición.");
        }

        return respuestaLogin;
    }
}
