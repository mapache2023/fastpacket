/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.modelo.pojo;

import java.util.Date;

/**
 *
 * @author hp
 */
public class Historial {
   private Date fechaCambio;
   private String comentario;
   private Integer idEnvio;
   private Integer idColaborador;
   private String colaborador;
   private Integer idEstatus;
   private String Estado;
public Historial(){
    
}

    public Historial(Date fechaCambio, String comentario, Integer idEnvio, Integer idColaborador, String colaborador, Integer idEstatus, String Estado) {
        this.fechaCambio = fechaCambio;
        this.comentario = comentario;
        this.idEnvio = idEnvio;
        this.idColaborador = idColaborador;
        this.colaborador = colaborador;
        this.idEstatus = idEstatus;
        this.Estado = Estado;
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

    public Integer getidColaborador() {
        return idColaborador;
    }

    public void setidColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getColaborador() {
        return colaborador;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }

    public Integer getIdEstado() {
        return idEstatus;
    }

    public void setIdEstado(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

   
        
}
