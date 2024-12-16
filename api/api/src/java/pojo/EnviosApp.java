/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.util.List;

/**
 *
 * @author hp
 */
public class EnviosApp {
    private String idEnvio;
    private String numeroGuia;
     private String origenDireccion;
     private String costo;
     private String conductor;
     private Integer idEstatus;
     private String estado;
     private String destinoDireccion;
     private Integer idCliente;
     private List<Paquete> paquetes;
     private List<Historial> cambios;
public EnviosApp(){}

    public EnviosApp(String idEnvio, String numeroGuia, String origenDireccion, String costo, String conductor, Integer idEstatus, String estado, String destinoDireccion, Integer idCliente, List<Paquete> paquetes, List<Historial> cambios) {
        this.idEnvio = idEnvio;
        this.numeroGuia = numeroGuia;
        this.origenDireccion = origenDireccion;
        this.costo = costo;
        this.conductor = conductor;
        this.idEstatus = idEstatus;
        this.estado = estado;
        this.destinoDireccion = destinoDireccion;
        this.idCliente = idCliente;
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

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
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

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }
   
}
