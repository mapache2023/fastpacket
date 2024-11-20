/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.ArrayList;
import java.util.List;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Envio;
import pojo.EnviosApp;
import pojo.Historial;
import pojo.Paquete;

/**
 *
 * @author hp
 */
public class ImpEnviosEspeciales {

    public static List<Envio> obtenerEnviosGuia(String numeroGuia) {
       List<Envio> envios = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
               envios = conexionBD.selectList("enviosEspeciales.obtenerEnviosGuia", numeroGuia);
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
               envios = conexionBD.selectList("enviosEspeciales.obtenerEnviosColaborador", idColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conexionBD.close();
            }
        }
        return envios;
    }

    public static List<Paquete> obtenerPaquetesEnvio(String idEnvio) {
  List<Paquete> paquetes = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
               paquetes = conexionBD.selectList("enviosEspeciales.obtenerPaquetesEnvio", idEnvio);
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conexionBD.close();
            }
        }return paquetes;
    }

    public static List<Historial> obtenerCambios(String idEnvio) {
     List<Historial> cambios = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
               cambios = conexionBD.selectList("enviosEspeciales.obtenerCambios", idEnvio);
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conexionBD.close();
            }
        }return cambios;}
    
}