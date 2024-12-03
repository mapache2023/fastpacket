/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.util.Date;

/**
 *
 * @author mapache
 */
public class Envio {
   private Integer idEnvio; 
private String origenDireccion; 
private String destinoDireccion; 
private String numeroGuia;
private String costo; 
private Date fechaActualizacion; 
private Integer idEstado;
private String estado;
private Integer idColaborador;
private String colaborador;
private Integer idCliente;
private String cliente;

    public Envio(){}

    public Envio(Integer idEnvio, String origenDireccion, String destinoDireccion, String numeroGuia, String costo, Date fechaActualizacion, Integer idEstado, String estado, Integer idColaborador, String colaborador, Integer idCliente, String cliente) {
        this.idEnvio = idEnvio;
        this.origenDireccion = origenDireccion;
        this.destinoDireccion = destinoDireccion;
        this.numeroGuia = numeroGuia;
        this.costo = costo;
        this.fechaActualizacion = fechaActualizacion;
        this.idEstado = idEstado;
        this.estado = estado;
        this.idColaborador = idColaborador;
        this.colaborador = colaborador;
        this.idCliente = idCliente;
        this.cliente = cliente;
    }
    
    @Override
    public String toString() {
        return numeroGuia;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getOrigenDireccion() {
        return origenDireccion;
    }

    public void setOrigenDireccion(String origenDireccion) {
        this.origenDireccion = origenDireccion;
    }

    public String getDestinoDireccion() {
        return destinoDireccion;
    }

    public void setDestinoDireccion(String destinoDireccion) {
        this.destinoDireccion = destinoDireccion;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getColaborador() {
        return colaborador;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
}
