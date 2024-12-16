package mybatis;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * La clase MyBatisUtil proporciona utilidades para gestionar la conexión a la base de datos 
 * mediante MyBatis. Configura el entorno de MyBatis y proporciona un método para obtener 
 * sesiones SQL que permiten interactuar con la base de datos.
 */
public class MyBatisUtil { 
    private static final String RUTA = "mybatis/mybatis-config.xml"; // Ruta del archivo de configuración de MyBatis
    private static final String AMBIENTE = "desarrollo"; // Entorno de configuración
    private static SqlSession conexion = null; // Conexión actual a la base de datos

    /**
     * Obtiene una nueva sesión SQL de la base de datos.
     * 
     * Este método intenta leer el archivo de configuración de MyBatis y crear un 
     * SqlSessionFactory. A partir de este, se abre una nueva sesión SQL. 
     * Si ocurre un error de entrada/salida (IOException) al intentar leer el 
     * archivo de configuración, este se maneja silenciosamente, y la conexión 
     * puede devolver null si no se establece correctamente.
     * 
     * @return Una instancia de SqlSession que permite realizar operaciones de 
     * acceso a datos. Puede devolver null si no se pudo establecer la conexión.
     */
    public static SqlSession getSession() {
        try {
            Reader reader = Resources.getResourceAsReader(RUTA);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader, AMBIENTE); 
            conexion = sqlMapper.openSession();
        } catch (IOException ex) {
            // Manejo de excepciones (silencioso)
        }
        return conexion;
    }
  public static SqlSession obtenerConexion() {
      SqlSession conexion = null;
        try {
            Reader reader = Resources.getResourceAsReader(RUTA);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader, AMBIENTE);
            conexion = sqlMapper.openSession();
        }catch (IOException ex) {
           ex.printStackTrace();
        }
        return conexion;
  }
}
