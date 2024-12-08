package desktop.modelo.dao;

import com.google.gson.Gson;
import desktop.modelo.ConexionWs;
import desktop.modelo.RespuestaHTTP;
import desktop.modelo.pojo.Historial;
import desktop.modelo.pojo.Mensaje;
import desktop.utilidades.Constantes;

import java.net.HttpURLConnection;

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

    //PARA PROBAR EL DAO
    public static void main(String[] args) {
        Integer idColaborador = 3;// este lo sacas del colaborador que esta en session
        Integer idEnvio =1;// este lo sacas al seleccionar un envio en la tabla
        Integer idEstatus =3; // este lo sacas del ComboBox
        String comentario = "son drogas xd"; // esto lo sacas del TexField o  TextArea
        Mensaje mensaje =hacerCambios(idColaborador,idEnvio,idEstatus,comentario);
        System.out.println(mensaje.getMensaje());
    }
}
