/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.modelo.dao;

import com.google.gson.Gson;
import desktop.modelo.ConexionWs;
import desktop.modelo.RespuestaHTTP;
import desktop.modelo.pojo.LoginColaborador;
import desktop.utilidades.Constantes;
import java.net.HttpURLConnection;

/**
 *
 * @author mapache
 */
public class LoginDAO {
    public static LoginColaborador iniciaSesion(String noPersonal, String password) {
     LoginColaborador respuestaws = new LoginColaborador();

        String url = Constantes.URI_WS +"login" ;
        String parametros = String.format("numeroPersonal=%s&contrasena=%s", noPersonal, password);

       RespuestaHTTP respuestaConexion = ConexionWs.peticionPOST(url, parametros);

        if (respuestaConexion.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {

            Gson gson = new Gson();
            respuestaws = gson.fromJson(respuestaConexion.getContenido(), LoginColaborador.class);

        } else {
            respuestaws.setError(true);
            respuestaws.setMensaje("error al realizar peticion");
        }
        return respuestaws; }
    
  
}
