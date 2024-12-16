/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.HashMap;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Mensaje;
import pojo.Unidad;

public class ImpUnidad {
     public static Mensaje registrarUnidad(Unidad unidad){
        Mensaje mensaje = new Mensaje();
        SqlSession conexion = MyBatisUtil.obtenerConexion();
        if(conexion != null){
            try{
             int filaAfectadas = conexion.insert("unidad.registroUnidad", unidad);
             conexion.commit();
             if(filaAfectadas > 0){
                 mensaje.setError(false);
                 mensaje.setMensaje("unidad"+unidad.getNumeroIdentificacion()+"fue registrado con exito");
             }else{
                 mensaje.setError(true);
                 mensaje.setMensaje("La unidad no pudo ser registrada");
             }
            }catch(Exception e){
                mensaje.setError(true);
                mensaje.setMensaje(e.getMessage());
            }
        }else{
            mensaje.setError(true);
            mensaje.setMensaje("El serrvicio no se encuenta disponible.");
        }
        return mensaje;
    }
     
     public static Mensaje editarUnidad(Unidad unidad){
         Mensaje mensaje = new Mensaje();
         SqlSession conexion = MyBatisUtil.obtenerConexion();
         if(conexion != null){
             try{
               int colaboradorEditar = conexion.update("unidad.editarUnidad", unidad);
               conexion.commit();
               if(colaboradorEditar > 0){
                   mensaje.setError(false);
                   mensaje.setMensaje("Se edito la unidad: "+unidad.getVin());
               }else{
                   mensaje.setError(true);
                   mensaje.setMensaje("No se pudo editar la unidad");
               }
           }catch(Exception e){
               mensaje.setError(true);
               mensaje.setMensaje(e.getMessage());
           }
       
       }else{
          mensaje.setError(true);
          mensaje.setMensaje("No se encuentra disponible el servicio");
       }
       return mensaje;
    }
     
    public static Unidad buscarUnidad(String vin, String marca, String numeroIdentificacion){
        Unidad respuesta = null;
        SqlSession conexion = MyBatisUtil.obtenerConexion();

        if(conexion != null){
            try {
                HashMap<String, String> parametros = new HashMap<>();
                parametros.put("vin", vin);
                parametros.put("marca", marca);
                parametros.put("numeroIdentificacion", numeroIdentificacion);
                respuesta = conexion.selectOne("unidad.buscarUnidad", parametros);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return respuesta;
    }
    
    /*  public static Unidad darDeBaja(String motivo){
        Unidad respuesta = null;
        SqlSession conexion = MyBatisUtil.obtenerConexion();
        
        if(conexion != null){
            try {
                HashMap<String, String> parametros = new HashMap<>();
                parametros.put("motivo", motivo);
                respuesta = conexion.selectOne("unidad.bajaUnidad",parametros);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return respuesta;
    } */

}
