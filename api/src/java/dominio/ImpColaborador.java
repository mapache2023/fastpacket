package dominio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.ws.rs.DELETE;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import pojo.Mensaje;

/**
 *
 * @author hp
 */
public class ImpColaborador {
      public static List<Colaborador> obtenerColaboradores(){
        List<Colaborador> colaboradors = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
                
                colaboradors = conexionBD.selectList("colaborador.obtenerColaboradores");
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conexionBD.close();
            }
        }
        return colaboradors;
    }
    
    public static Mensaje registrarColaborador(Colaborador colaborador){
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
                Integer idColaboradorExistente = conexionBD.selectOne("colaborador.buscarColaboradorCurp", colaborador.getCurp());
                if(idColaboradorExistente!= null){
                      msj.setMensaje("Error: El curp ingresado ya se encuentra registrado.");
                return msj;
                }
                int filasAfectadas = conexionBD.insert("colaborador.registrar", colaborador);
                conexionBD.commit();
                if(filasAfectadas > 0){
                    msj.setError(false);
                    msj.setMensaje("Información del colaborador registrada con éxito.");
                }else{
                    msj.setMensaje("Lo sentimos, por el momento no se puede registrar la información del colaborador");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: "+e.getMessage());
            } finally{
                conexionBD.close();
            }
        }else{
            msj.setMensaje("Error al registrar la información, por el momento no hay conexión con la base de datos.");
        }
        return msj;
    }
    
    public static Mensaje editarColaborador(Colaborador colaborador){
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
                int filasAfectadas = conexionBD.update("colaborador.editar", colaborador);
                conexionBD.commit();
                if(filasAfectadas > 0){
                    msj.setError(false);
                    msj.setMensaje("Informacion del colaborador editada con exito.");
                }else{
                    msj.setMensaje("Lo sentimos, por el momento no se puede editar la información del colaborador");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: "+e.getMessage());
            } finally{
                conexionBD.close();
            }
        }else{
            msj.setMensaje("Error al registrar la información, por el momento no hay conexión con la base de datos.");
        }
        return msj;
    }
    
    public static Mensaje registrarFotoColaborador(int idColaborador, byte[] foto){
        Mensaje msj = new Mensaje();
        msj.setError(true);
        LinkedHashMap<String, Object> parametros = new LinkedHashMap<>();
        parametros.put("idColaborador", idColaborador);
        parametros.put("foto", foto);
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
                int filasAfectadas = conexionBD.update("colaborador.guardarFoto", parametros);
                conexionBD.commit();
                if(filasAfectadas > 0){
                    msj.setError(false);
                    msj.setMensaje("Fotografía del colaborador guardada correctamente.");
                }else{
                    msj.setMensaje("Lo sentimos hubo un error al intentar guardar la "
                            + "fotografía, por favor inténtelo más tarde.");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: "+e.getMessage());
            } finally{
                conexionBD.close();
            }
        }else{
            msj.setMensaje("Error de conexión, por el momento no se puede registrar la "
                    + "fotografía del colaborador.");
        }
        return msj;
    }
    
    public static Colaborador obtenerFotografiaColaborador(int idColaborador){
        Colaborador colaborador = null;
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
                colaborador = conexionBD.selectOne("colaborador.obtenerFoto", idColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conexionBD.close();
            }
        }
        return colaborador;
    }
    
  public static Mensaje asignarUnidad(Integer idUnidad,Integer idColaborador){  
      Mensaje msj = new Mensaje();
    msj.setError(true);
    LinkedHashMap<String, Object> parametros = new LinkedHashMap<>();
    parametros.put("idColaborador", idColaborador);
    parametros.put("idUnidad", idUnidad);
    
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    if (conexionBD != null) {
        try {
            // Verificar si la unidad ya está asignada a otro colaborador
            Integer idColaboradorExistente = conexionBD.selectOne("colaborador.buscarColaboradorUnidad", idUnidad);

            // Si ya existe un colaborador asignado a esta unidad
            if (idColaboradorExistente != null && !idColaboradorExistente.equals(idColaborador)) {
                msj.setMensaje("Error: La unidad ya está asignada a otro colaborador o desasine la unidad.");
                return msj;
            }
            
            // Si no está asignada a otro colaborador, proceder con el cambio de unidad
            int filasAfectadas = conexionBD.update("colaborador.asignar", parametros);
            conexionBD.commit();
            
            if (filasAfectadas > 0) {
                msj.setError(false);
                msj.setMensaje("La unidad se cambió correctamente.");
            } else {
                msj.setMensaje("Lo sentimos, hubo un error inesperado.");
            }
        } catch (Exception e) {
            msj.setMensaje("Error: " + e.getMessage());
        } finally {
            conexionBD.close();
        }
    } else {
        msj.setMensaje("Error de conexión, por el momento no se puede realizar la operación.");
    }
    return msj;
  }
  
public static Mensaje cambiarUnidad(    Integer idUnidad, Integer idColaborador) {
    Mensaje msj = new Mensaje();
    msj.setError(true);
    LinkedHashMap<String, Object> parametros = new LinkedHashMap<>();
    parametros.put("idColaborador", idColaborador);
    parametros.put("idUnidad", idUnidad);
    
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    if (conexionBD != null) {
        try {
            // Verificar si la unidad ya está asignada a otro colaborador
            Integer idColaboradorExistente = conexionBD.selectOne("colaborador.buscarColaboradorUnidad", idUnidad);

            // Si ya existe un colaborador asignado a esta unidad
            if (idColaboradorExistente != null && !idColaboradorExistente.equals(idColaborador)) {
                msj.setMensaje("Error: La unidad ya está asignada a otro colaborador.");
                return msj;
            }
            
            // Si no está asignada a otro colaborador, proceder con el cambio de unidad
            int filasAfectadas = conexionBD.update("colaborador.cambiar", parametros);
            conexionBD.commit();
            
            if (filasAfectadas > 0) {
                msj.setError(false);
                msj.setMensaje("La unidad se cambió correctamente.");
            } else {
                msj.setMensaje("Lo sentimos, hubo un error inesperado.");
            }
        } catch (Exception e) {
            msj.setMensaje("Error: " + e.getMessage());
        } finally {
            conexionBD.close();
        }
    } else {
        msj.setMensaje("Error de conexión, por el momento no se puede realizar la operación.");
    }
    return msj;
}

  
    public static Mensaje desasignarUnidad(Integer idUnidad){
      Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
                
                int filasAfectadas = conexionBD.update("colaborador.desasignar", idUnidad);
                conexionBD.commit();
                if(filasAfectadas > 0){
                    msj.setError(false);
                    msj.setMensaje("la unidad se desasigno correctamente.");
                }else{
                    msj.setMensaje("Lo sentimos hubo un error inesperado.");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: "+e.getMessage());
            } finally{
                conexionBD.close();
            }
        }else{
            msj.setMensaje("Error de conexión, por el momento no se puede registrar la "
                    + "fotografía del colaborador.");
        }
        return msj;}

    public static Mensaje eliminar(String IdColaborador) {
 Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
                int filasAfectadas = conexionBD.update("colaborador.eliminar", IdColaborador);
                conexionBD.commit();
                if(filasAfectadas > 0){
                    msj.setError(false);
                    msj.setMensaje("El colaborador se elimino  con exito.");
                }else{
                    msj.setMensaje("Lo sentimos, por el momento no se puede eliminar la información del colaborador");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: "+e.getMessage());
            } finally{
                conexionBD.close();
            }
        }else{
            msj.setMensaje("Error al eliminar la información, por el momento no hay conexión con la base de datos.");
        }
        return msj;
    }
    
       
}

