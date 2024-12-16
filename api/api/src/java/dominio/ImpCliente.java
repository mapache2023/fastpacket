package dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Cliente;
import pojo.Mensaje;

public class ImpCliente {
    
    public static Mensaje registrarCliente(Cliente cliente) {
        Mensaje msj;
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {
                // Inserta el cliente en la base de datos utilizando el mapper correspondiente
                int filasAfectadas = conexionBD.insert("Cliente.registro", cliente);
                
                // Si commit es necesario en tu configuración
                conexionBD.commit(); 

                if (filasAfectadas > 0) {
                    // Registro exitoso
                    msj = new Mensaje(false, 
                        "El cliente " + cliente.getNombre() + " " +
                        cliente.getApellidoPaterno() + " " +
                        cliente.getApellidoMaterno() + " fue registrado con éxito.",
                        cliente);
                } else {
                    // No se pudo registrar el cliente
                    msj = new Mensaje(true, "El cliente no pudo ser registrado.", null);
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
        return null; // Devuelve null si no se encuentra el cliente
    }

    // Método para editar un cliente
    public static boolean guardarCambios(Cliente cliente) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.update("Cliente.actualizarCliente", cliente);
                conexionBD.commit();  // Si commit es necesario
                return filasAfectadas > 0;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return false; // Devuelve false si no se pudo guardar
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
        return null; // Devuelve null si ocurre un error
    }

    public static Mensaje eliminarCliente(Integer idCliente) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.delete("Cliente.eliminarCliente", idCliente);
                conexionBD.commit();  // Asegúrate de hacer commit después de la eliminación

                if (filasAfectadas > 0) {
                    return new Mensaje(false, "Cliente eliminado correctamente.", null);
                } else {
                    return new Mensaje(true, "Cliente no encontrado.", null);
                }
            } catch (Exception e) {
                e.printStackTrace();  // Imprime el error para diagnóstico
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
