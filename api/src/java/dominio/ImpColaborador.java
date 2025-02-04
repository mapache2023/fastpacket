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
import pojo.Colaborador;
import pojo.Mensaje;
import pojo.Rol;
import pojo.Unidad;

/**
 *
 * @author hp
 */
public class ImpColaborador {

    public static List<Colaborador> obtenerColaboradores() {
        List<Colaborador> colaboradores = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {

                colaboradores = conexionBD.selectList("colaborador.obtenerColaboradores");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return colaboradores;
    }

    public static Mensaje registrarColaborador(Colaborador colaborador) {
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                Integer idColaboradorExistente = conexionBD.selectOne("colaborador.buscarColaboradorCurp", colaborador.getCurp());
                if (idColaboradorExistente != null) {
                    msj.setMensaje("Error: El curp ingresado ya se encuentra registrado.");
                    return msj;
                }
                int filasAfectadas = conexionBD.insert("colaborador.registrar", colaborador);
                conexionBD.commit();
                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("Informacion del colaborador registrada con exito.");
                } else {
                    msj.setMensaje("Lo sentimos, por el momento no se puede registrar la informacion del colaborador");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            msj.setMensaje("Error al registrar la informacion, por el momento no hay conexion con la base de datos.");
        }
        return msj;
    }

    public static Mensaje editarColaborador(Colaborador colaborador) {
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.update("colaborador.editar", colaborador);
                conexionBD.commit();
                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("Informacion del colaborador editada con exito.");
                } else {
                    msj.setMensaje("Lo sentimos, por el momento no se puede editar la informacion del colaborador");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            msj.setMensaje("Error al registrar la informacion, por el momento no hay conexión con la base de datos.");
        }
        return msj;
    }

    public static Mensaje registrarFotoColaborador(int idColaborador, byte[] foto) {
        Mensaje msj = new Mensaje();
        msj.setError(true);
        LinkedHashMap<String, Object> parametros = new LinkedHashMap<>();
        parametros.put("idColaborador", idColaborador);
        parametros.put("foto", foto);
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.update("colaborador.guardarFoto", parametros);
                conexionBD.commit();
                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("Fotografia del colaborador guardada correctamente.");
                } else {
                    msj.setMensaje("Lo sentimos hubo un error al intentar guardar la "
                            + "fotografia, por favor inténtelo mas tarde.");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            msj.setMensaje("Error de conexion, por el momento no se puede registrar la "
                    + "fotografia del colaborador.");
        }
        return msj;
    }

    public static Colaborador obtenerFotografiaColaborador(int idColaborador) {
        Colaborador colaborador = null;
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                colaborador = conexionBD.selectOne("colaborador.obtenerFoto", idColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return colaborador;
    }

    public static Mensaje asignarUnidad(Integer idUnidad, Integer idColaborador) {
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
                    msj.setMensaje("La unidad se cambio correctamente.");
                } else {
                    msj.setMensaje("Lo sentimos, hubo un error inesperado.");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            msj.setMensaje("Error de conexión, por el momento no se puede realizar la operacion.");
        }
        return msj;
    }

    public static Mensaje cambiarUnidad(Integer idUnidad, Integer idColaborador) {
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
                    msj.setMensaje("Error: La unidad ya esta asignada a otro colaborador.");
                    return msj;
                }

                // Si no está asignada a otro colaborador, proceder con el cambio de unidad
                int filasAfectadas = conexionBD.update("colaborador.cambiar", parametros);
                conexionBD.commit();

                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("La unidad se cambio correctamente.");
                } else {
                    msj.setMensaje("Lo sentimos, hubo un error inesperado.");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            msj.setMensaje("Error de conexion, por el momento no se puede realizar la operación.");
        }
        return msj;
    }

    public static Mensaje desasignarUnidad(Integer idUnidad) {
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {

                int filasAfectadas = conexionBD.update("colaborador.desasignar", idUnidad);
                conexionBD.commit();
                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("la unidad se desasigno correctamente.");
                } else {
                    msj.setMensaje("Lo sentimos hubo un error inesperado.");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            msj.setMensaje("Error de conexión, por el momento no se puede conectar a la bd.");
        }
        return msj;
    }

    public static Mensaje eliminar(String IdColaborador) {
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.update("colaborador.eliminar", IdColaborador);
                conexionBD.commit();
                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("El colaborador se elimino  con exito.");
                } else {
                    msj.setMensaje("Lo sentimos, por el momento no se puede eliminar la información del colaborador");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            msj.setMensaje("Error al eliminar la información, por el momento no hay conexión con la base de datos.");
        }
        return msj;
    }

    // Función para buscar colaboradores por nombre
    public static List<Colaborador> buscarPorNombre(String nombre) {
        List<Colaborador> colaboradores = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                colaboradores = conexionBD.selectList("colaborador.buscarPorNombre", nombre);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return colaboradores;
    }

    // Función para buscar colaboradores por número de personal
    public static Colaborador buscarPorNumeroPersonal(String numeroPersonal) {
        Colaborador colaborador = null;
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                colaborador = conexionBD.selectOne("colaborador.buscarPorNumeroPersonal", numeroPersonal);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return colaborador;
    }

    // Función para buscar colaboradores por rol
    public static List<Colaborador> buscarPorRol(String rol) {
        List<Colaborador> colaboradores = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                colaboradores = conexionBD.selectList("colaborador.buscarPorRol", rol);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return colaboradores;
    }
    
  public static List<Colaborador> conductores() {
        List<Colaborador> colaboradors = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {

                colaboradors = conexionBD.selectList("colaborador.conductores");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return colaboradors;}
  
    public static List<Unidad> unidadesActivas() {
        List<Unidad> unidades = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {

                unidades = conexionBD.selectList("colaborador.unidadesActivas");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return unidades;
    }
    public static List<Rol> roles() {
        List<Rol> roles = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {

                roles = conexionBD.selectList("colaborador.roles");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return roles;
    }
}
