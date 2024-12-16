package desktop.modelo.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import desktop.modelo.ConexionWs;
import desktop.modelo.RespuestaHTTP;
import desktop.modelo.pojo.Historial;
import desktop.modelo.pojo.Mensaje;
import desktop.modelo.pojo.Envio;
import desktop.utilidades.Constantes;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class EnvioDAO {
    public static Mensaje hacerCambios(Integer idColaborador,Integer idEnvio,Integer idEstatus,String cometario){
        Mensaje msj = new Mensaje();
        Historial historial = new Historial();
        historial.setidColaborador(idColaborador);
        historial.setIdEnvio(idEnvio);
        historial.setIdEstado(idEstatus);
        historial.setComentario(cometario);
        String url = Constantes.URI_WS + "enviosEspeciales/app/cambios";
        Gson gson = new Gson();
        String parametros = gson.toJson(historial);
        RespuestaHTTP respuesta = ConexionWs.peticionPOSTjson(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {

            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            msj.setError(true);
            msj.setMensaje("Error al asignar cambios de colaborador.");
        }
        return msj;

    }
    public static List<Envio> obtenerEnvios() {
        List<Envio> envios = new ArrayList<>();
        String url = Constantes.URI_WS + "envio/consultar-todos-envios";
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Type tipoListaEnvios = new TypeToken<List<Envio>>(){}.getType();
            Gson gson = new Gson();
            envios = gson.fromJson(respuesta.getContenido(), tipoListaEnvios);
        }

        return envios;
    }

    //PARA PROBAR EL DAO
    public static void main(String[] args) {
       System.out.println(obtenerEnvios());
    }
}
