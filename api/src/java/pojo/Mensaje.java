
package pojo;

public class Mensaje {
    private Boolean error;
    private String mensaje;
    private String contenido;  

    public Mensaje() {
    }

    public Mensaje(Boolean error, String mensaje, String contenido) {
        this.error = error;
        this.mensaje = mensaje;
        this.contenido = contenido;
    }

    // Getter y setters
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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}


