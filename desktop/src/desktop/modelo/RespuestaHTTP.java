/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.modelo;

/**
 *
 * @author mapache
 */
public class RespuestaHTTP {
        private Integer codigoRespuesta;
        private String contenido;
     public RespuestaHTTP(){}
    public RespuestaHTTP(Integer codigorRespuesta, String contenido) {
        this.codigoRespuesta = codigorRespuesta;
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Integer getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(Integer codigorRespuesta) {
        this.codigoRespuesta = codigorRespuesta;
    }
        
    
}
