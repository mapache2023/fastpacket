package pojo;

import java.util.List;

public class EnviosApp {
    private String idEnvio;
    private String numeroGuia;
     private String origenDireccion;
     private String costo;
     private Integer idEstatus;
     private String estado;
     private String destinoDireccion;
     private List<Paquete> paquetes;
     private List<Historial> cambios;
     
public EnviosApp(){}
    public EnviosApp(String idEnvio, String numeroGuia, String origenDireccion, String costo, Integer idEstatus, String estado, String destinoDireccion, List<Paquete> paquetes, List<Historial> cambios) {
        this.idEnvio = idEnvio;
        this.numeroGuia = numeroGuia;
        this.origenDireccion = origenDireccion;
        this.costo = costo;
        this.idEstatus = idEstatus;
        this.estado = estado;
        this.destinoDireccion = destinoDireccion;
        this.paquetes = paquetes;
        this.cambios = cambios;
    }

    public String getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public String getOrigenDireccion() {
        return origenDireccion;
    }

    public void setOrigenDireccion(String origenDireccion) {
        this.origenDireccion = origenDireccion;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDestinoDireccion() {
        return destinoDireccion;
    }

    public void setDestinoDireccion(String destinoDireccion) {
        this.destinoDireccion = destinoDireccion;
    }

    public List<Paquete> getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(List<Paquete> paquetes) {
        this.paquetes = paquetes;
    }

    public List<Historial> getCambios() {
        return cambios;
    }

    public void setCambios(List<Historial> cambios) {
        this.cambios = cambios;
    }
     
}
