/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.util.Date;

/**
 *
 * @author hp
 */
public class Historial {
   private Date fechaCambio;
   private String comentario;
   private Integer idEnvio;
   private Integer IdColaborador;
   private String colaborador;
   private Integer idRol;
   private String rol;
public Historial(){
    
}

    public Historial(Date fechaCambio, String comentario, Integer idEnvio, Integer IdColaborador, String colaborador, Integer idRol, String rol) {
        this.fechaCambio = fechaCambio;
        this.comentario = comentario;
        this.idEnvio = idEnvio;
        this.IdColaborador = IdColaborador;
        this.colaborador = colaborador;
        this.idRol = idRol;
        this.rol = rol;
    }

    public Date getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Integer getIdColaborador() {
        return IdColaborador;
    }

    public void setIdColaborador(Integer IdColaborador) {
        this.IdColaborador = IdColaborador;
    }

    public String getColaborador() {
        return colaborador;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
   
        
}
