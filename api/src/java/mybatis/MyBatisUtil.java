package mybatis;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil { 
    private static final String RUTA = "mybatis/mybatis-config.xml";
    private static final String AMBIENTE = "desarrollo"; 
    private static SqlSession conexion = null; 

    public static SqlSession getSession() {
        try {
            Reader reader = Resources.getResourceAsReader(RUTA);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader, AMBIENTE); 
            conexion = sqlMapper.openSession();
        } catch (IOException ex) {
          
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
