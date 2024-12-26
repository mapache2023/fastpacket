/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.HashMap;
import java.util.List;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import pojo.Mensaje;
import pojo.Unidad;
import pojo.Tipo;

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
     
   /* public static Unidad buscarUnidad(String vin, String marca, String numeroIdentificacion){
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
    }*/
     
    
     public static Unidad buscarUnidad(String vin){
        Unidad respuesta = null;
        SqlSession conexion = MyBatisUtil.obtenerConexion();

        if(conexion != null){
            try {
                HashMap<String, String> parametros = new HashMap<>();
                parametros.put("vin", vin);
                respuesta = conexion.selectOne("unidad.buscarUnidadVin", parametros);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return respuesta;
    }
     
      public static List<Unidad> buscarUnidadMarca(String marca){
        List<Unidad> respuesta = null;
        SqlSession conexion = MyBatisUtil.obtenerConexion();

        if(conexion != null){
            try {
                HashMap<String, String> parametros = new HashMap<>();
                parametros.put("marca", marca);
                respuesta = conexion.selectList("unidad.buscarUnidadMarca", parametros);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return respuesta;
    }
      
      public static Unidad buscarUnidadNumero(String numeroIdentificacion){
        Unidad respuesta = null;
        SqlSession conexion = MyBatisUtil.obtenerConexion();

        if(conexion != null){
            try {
                HashMap<String, String> parametros = new HashMap<>();
                parametros.put("numeroIdentificacion", numeroIdentificacion);
                respuesta = conexion.selectOne("unidad.buscarUnidadNumero", parametros);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return respuesta;
    }
    
    
      public static Mensaje darDeBaja(Integer idUnidad, String motivo){
        Mensaje mensaje = new Mensaje();
        SqlSession conexion = MyBatisUtil.obtenerConexion();
        mensaje.setError(false);
        if(conexion != null){
            try {
                HashMap<String, Object> parametros = new HashMap<>();
                parametros.put("idUnidad", idUnidad);
                parametros.put("motivo", motivo);
               
                Integer filas = conexion.update("unidad.bajaUnidad",parametros);
                 conexion.commit();
                if (filas>0) {
                    mensaje.setContenido("se dio de baja con exito");
                }else{
                    mensaje.setContenido("eror no se dio de baja");
                    mensaje.setError(Boolean.TRUE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mensaje;
    } 
      
    public static List<Unidad> obtenerUnidades(){
        List<Unidad> unidades = null;
        
        SqlSession conexion = MyBatisUtil.obtenerConexion();
        
        if(conexion != null){
            try{
                unidades = conexion.selectList("unidad.obtenerUnidad");
            }catch(Exception e){
                e.getMessage();
            }
        }
        
        return unidades;
    }
    
    public static List<Tipo> obtenerTipoUnidades(){
        List<Tipo> unidadesTipo = null;
        
        SqlSession conexion = MyBatisUtil.obtenerConexion();
        
        if(conexion != null){
            try{
                unidadesTipo = conexion.selectList("unidad.tipoUnidad");
            }catch(Exception e){
                e.getMessage();
            }
        }
        
        return unidadesTipo;
    }
    
    public static List<Colaborador> obtenerCoductor(){
        List<Colaborador> conductores = null;
        
        SqlSession conexion = MyBatisUtil.obtenerConexion();
        
        if(conexion != null){
            try{
                conductores = conexion.selectList("unidad.obtenerCoductor");
            }catch(Exception e){
                e.getMessage();
            }
        }
        return conductores;
    }
}
