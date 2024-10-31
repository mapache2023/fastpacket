/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.HashMap;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import pojo.LoginColaborador;

/**
 *
 * @author mapache
 */
public class ImpLogin {

     public static LoginColaborador login(String numeroPersonal, String contrasena) {
        LoginColaborador respuesta = new LoginColaborador();
        respuesta.setError(true);
        SqlSession sqlSession = MyBatisUtil.getSession();
        
        if (sqlSession == null) {
            respuesta.setMensaje("error no conexion a bd");
        } else {
            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("numeroPersonal", numeroPersonal);
            parametros.put("contrasena", contrasena);
            Colaborador colaborador = sqlSession.selectOne("colaboradorL.login", parametros);
            
            if (colaborador == null) {
                respuesta.setMensaje("contraseña y/o no incorrecto");
            } else {
                respuesta.setColaborador(colaborador);
                respuesta.setError(false);
                respuesta.setMensaje("bienvenido " + colaborador.getNombre());
            }
        }
        return respuesta;
    }
    
}
