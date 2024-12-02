
package dominio;


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
                int filasAfectadas = conexionBD.insert("Paquete.registro", paquete);
                conexionBD.commit();

                if (filasAfectadas > 0) {
                    // Registro exitoso
                    msj = new Mensaje(false, 
                        "El paquete con descripción \"" + paquete.getDescripcion() + 
                        "\" fue registrado con éxito.", paquete);
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
}

