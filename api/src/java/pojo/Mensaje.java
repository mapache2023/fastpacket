/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

/**
 *
 * @author mapache
 */
public class Mensaje {
    
    private Boolean error;
    private String mensaje;
    private Object contenido;

    public Mensaje() {
    }

    public Mensaje(Boolean error, String mensaje, Object contenido) {
        this.error = error;
        this.mensaje = mensaje;
        this.contenido = contenido;
    }


    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Object getContenido() {
        return contenido;
    }

    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }
    
}

