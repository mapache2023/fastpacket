
package dominio;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Cliente;
import pojo.Mensaje;


public class ImpCliente {

    // Método para registrar un cliente
   public static Mensaje registrarCliente(Cliente cliente) {
    Mensaje msj;
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    Gson gson = new Gson();

    if (conexionBD != null) {
        try {
            int filasAfectadas = conexionBD.insert("Cliente.registro", cliente);
            conexionBD.commit();

            if (filasAfectadas > 0) {
                // Convertir el cliente registrado a JSON
                String clienteJson = gson.toJson(cliente);
                msj = new Mensaje(
                    false,
                    "El cliente " + cliente.getNombre() + " " + cliente.getApellidoPaterno() +
                    " fue registrado con éxito.",
                    clienteJson
                );
            } else {
                msj = new Mensaje(true, "El cliente no pudo ser registrado.", null);
            }
        } catch (Exception e) {
            msj = new Mensaje(true, e.getMessage(), null);
        } finally {
            conexionBD.close();
        }
    } else {
        msj = new Mensaje(true, "Por el momento el servicio no está disponible.", null);
    }

    return msj;
}
    // Método para buscar cliente por ID
    public static Cliente buscarCliente(Integer idCliente) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {
                return conexionBD.selectOne("Cliente.obtenerClientePorId", idCliente);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    // Método para editar un cliente
    public static boolean guardarCambios(Cliente cliente) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.update("Cliente.actualizarCliente", cliente);
                conexionBD.commit();
                return filasAfectadas > 0;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return false;
    }

    // Método para obtener todos los clientes
    public static List<Cliente> obtenerClientes() {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {
                return conexionBD.selectList("Cliente.obtenerClientes");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

  


    // Método para eliminar un cliente
    public static Mensaje eliminarCliente(Integer idCliente) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.delete("Cliente.eliminarCliente", idCliente);
                conexionBD.commit();

                if (filasAfectadas > 0) {
                    return new Mensaje(false, "Cliente eliminado correctamente.", null);
                } else {
                    return new Mensaje(true, "Cliente no encontrado.", null);
                }
            } catch (Exception e) {
                return new Mensaje(true, "Error al eliminar el cliente: " + e.getMessage(), null);
            } finally {
                conexionBD.close();
            }
        }
        return new Mensaje(true, "Error al conectar con la base de datos.", null);
    }



    // Método para buscar clientes por nombre, teléfono o correo
    public static List<Cliente> buscarCliente(String nombre, String telefono, String correo) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        List<Cliente> clientes = new ArrayList<>();

        if (conexionBD != null) {
            try {
                // Crear un mapa para pasar los parámetros a MyBatis
                Map<String, Object> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("telefono", telefono);
                params.put("correo", correo);

                // Ejecutar la consulta en MyBatis, pasando los parámetros
                clientes = conexionBD.selectList("Cliente.buscarCliente", params);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return clientes;  // Devuelve la lista de clientes encontrados
    }
}




