/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Cliente;
import pojo.EnviosApp;
import pojo.Historial;
import pojo.Mensaje;


/**
 *
 * @author hp
 */
public class ImpEnviosEspeciales {

    public static EnviosApp obtenerEnviosGuia(String numeroGuia) {
       EnviosApp envios = new EnviosApp();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
               envios = conexionBD.selectOne("EnvioNumeroGuia", numeroGuia);
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conexionBD.close();
            }
        }
        return envios;
    }

    public static List<EnviosApp> obtenerEnvioColaborador(String idColaborador) {
   List<EnviosApp> envios = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
               envios = conexionBD.selectList("enviosEspeciales.EnvioColaborador", idColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conexionBD.close();
            }
        }
        return envios;
    }

    public static Cliente obtenerCliente(String idCliente) {
        Cliente cliente = new Cliente();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
               cliente = conexionBD.selectOne("EnvioCliente",idCliente);
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conexionBD.close();
            }
        }
        return cliente;
    }
   
    public static Mensaje registrarCambio(Historial historial){
        Mensaje msj = new Mensaje();
        msj.setError(true);
        LinkedHashMap<String, Object> parametros = new LinkedHashMap<>();
        parametros.put("idEstado", historial.getIdEstado());
        parametros.put("idEnvio", historial.getIdEnvio());
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
                 int filasAfectadas = conexionBD.update("agregarHistorial", historial);
                int filasAfectadas2 = conexionBD.update("enviosEspeciales.actualizarEstadoEnvio", parametros);
                conexionBD.commit();
                if(filasAfectadas > 0){
                    msj.setError(false);
                    msj.setMensaje("Cambio del colaborador guardado correctamente.");
                }else{
                    msj.setMensaje("Lo sentimos hubo un error al intentar guardar");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: "+e.getMessage());
            } finally{
                conexionBD.close();
            }
        }else{
            msj.setMensaje("Error de conexión, por el momento no se puede registrar cambio");
        }
        return msj;
    }

    }

  
   
