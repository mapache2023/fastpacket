package dominio;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Cliente;
import pojo.Envio;
import pojo.EnviosApp;
import pojo.Historial;
import pojo.Paquete;


public class ImpEnviosEspeciales {

    public static EnviosApp obtenerEnviosGuia(String numeroGuia) {
       EnviosApp envios = new EnviosApp();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
               envios = conexionBD.selectOne("EnvioNumeroGuia", numeroGuia);
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conexionBD.close();
            }
        }
        return envios;
    }

    public static List<EnviosApp> obtenerEnvioColaborador(String idColaborador) {
   List<EnviosApp> envios = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
               envios = conexionBD.selectList("enviosEspeciales.EnvioColaborador", idColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conexionBD.close();
            }
        }
        return envios;
    }

    public static Cliente obtenerCliente(String idCliente) {
        Cliente cliente = new Cliente();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
               cliente = conexionBD.selectOne("EnvioCliente",idCliente);
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conexionBD.close();
            }
        }
        return cliente;
    }
   
    }

  
   
