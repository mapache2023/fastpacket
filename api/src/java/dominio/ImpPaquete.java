
package dominio;

import com.google.gson.Gson;
import java.util.List;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Mensaje;
import pojo.Paquete;


public class ImpPaquete {
public static Mensaje registrarPaquete(Paquete paquete) {
    Mensaje msj;
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();

    if (conexionBD != null) {
        try {
            // Inserta el paquete en la base de datos utilizando el mapper correspondiente
            int filasAfectadas = conexionBD.insert("paquete.registro", paquete);
            conexionBD.commit();

            if (filasAfectadas > 0) {
                // Registro exitoso
                Gson gson = new Gson();
                String paqueteJson = gson.toJson(paquete); // Convertir el objeto Paquete a JSON
                msj = new Mensaje(false, 
                    "El paquete con descripción \"" + paquete.getDescripcion() + 
                    "\" fue registrado con éxito.", paqueteJson);
            } else {
                // No se pudo registrar el paquete
                msj = new Mensaje(true, "El paquete no pudo ser registrado.", null);
            }
        } catch (Exception e) {
            // Manejo de errores durante la operación
            msj = new Mensaje(true, e.getMessage(), null);
        } finally {
            conexionBD.close(); // Asegura cerrar la conexión
        }
    } else {
        // Error al obtener la conexión
        msj = new Mensaje(true, "Por el momento el servicio no está disponible.", null);
    }

    return msj;
}

    public static Paquete buscarPaquete(Integer idPaquete) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        Paquete paquete = null;

        if (conexionBD != null) {
            try {
                // Busca el paquete en la base de datos utilizando el mapper correspondiente
                paquete = conexionBD.selectOne("paquete.buscarPaquete", idPaquete); // Cambio para coincidir con el mapper
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close(); // Asegura cerrar la conexión
            }
        }
        return paquete; // Devuelve el paquete encontrado o null si no existe
    }

    public static boolean guardarCambios(Paquete paquete) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        boolean actualizado = false;

        if (conexionBD != null) {
            try {
                // Actualiza el paquete en la base de datos utilizando el mapper correspondiente
                int filasAfectadas = conexionBD.update("paquete.actualizarPaquete", paquete); // Cambio para coincidir con el mapper
                conexionBD.commit();

                // Si se actualizaron filas, la operación fue exitosa
                if (filasAfectadas > 0) {
                    actualizado = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close(); // Asegura cerrar la conexión
            }
        }
        return actualizado; // Devuelve si la actualización fue exitosa o no
    }
public static List<Paquete> buscarPaquetesPorEnvio(Integer idEnvio) {
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    List<Paquete> paquetes = null;

    if (conexionBD != null) {
        try {
            paquetes = conexionBD.selectList("paquete.buscarPaquetesPorEnvio", idEnvio);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexionBD.close();
        }
    }
    return paquetes;
}
public static List<Paquete> buscarPaquetesPorGuia(String numeroGuia) {
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    List<Paquete> paquetes = null;

    if (conexionBD != null) {
        try {
            // Consulta por número de guía en el mapeador
            paquetes = conexionBD.selectList("paquete.buscarPaquetesPorGuia", numeroGuia);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexionBD.close();
        }
    }
    return paquetes;
}

public static Mensaje eliminarPaquete(Integer idPaquete, Integer idEnvio) {
    Mensaje msj;
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();

    if (conexionBD != null) {
        try {
            // Crear un objeto Paquete con el idPaquete e idEnvio, dejando los demás campos como null
            Paquete paqueteAEliminar = new Paquete(idPaquete, null, null, null, null, null, idEnvio, null);

            // Elimina el paquete utilizando el mapper correspondiente
            int filasAfectadas = conexionBD.delete("paquete.eliminar", paqueteAEliminar);
            conexionBD.commit();

            if (filasAfectadas > 0) {
                // Eliminación exitosa
                msj = new Mensaje(false, 
                    "El paquete con ID \"" + idPaquete + 
                    "\" fue eliminado del envío con ID \"" + idEnvio + "\" correctamente.", null);
            } else {
                // No se pudo eliminar el paquete
                msj = new Mensaje(true, "El paquete no pudo ser eliminado del envío.", null);
            }
        } catch (Exception e) {
            // Manejo de errores durante la operación
            msj = new Mensaje(true, e.getMessage(), null);
        } finally {
            conexionBD.close(); // Asegura cerrar la conexión
        }
    } else {
        // Error al obtener la conexión
        msj = new Mensaje(true, "Por el momento el servicio no está disponible.", null);
    }

    return msj;
}


  // Método para obtener todos los clientes
    public static List<Paquete> obtenerPaquetes() {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {
                return conexionBD.selectList("paquete.obtenerPaquetes");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return null; // Devuelve null si ocurre un error
    }


}





