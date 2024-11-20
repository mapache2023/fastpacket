/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

/**
 *
 * @author hp
 */
public class ColaboradorDesktop {
    private Integer IdColaborador;
    private Integer IdRol;
    private String nombre;
    private String apellidoMatrno;
    private String apellidoPaterno;
    private String fotografiaBase64;
    public ColaboradorDesktop(){
    }

    public ColaboradorDesktop(Integer IdColaborador, Integer IdRol, String nombre, String apellidoMatrno, String apellidoPaterno, String fotografiaBase64) {
        this.IdColaborador = IdColaborador;
        this.IdRol = IdRol;
        this.nombre = nombre;
        this.apellidoMatrno = apellidoMatrno;
        this.apellidoPaterno = apellidoPaterno;
        this.fotografiaBase64 = fotografiaBase64;
    }

    public String getFotografiaBase64() {
        return fotografiaBase64;
    }

    public void setFotografiaBase64(String fotografiaBase64) {
        this.fotografiaBase64 = fotografiaBase64;
    }

    public Integer getIdColaborador() {
        return IdColaborador;
    }

    public void setIdColaborador(Integer IdColaborador) {
        this.IdColaborador = IdColaborador;
    }

    public Integer getIdRol() {
        return IdRol;
    }

    public void setIdRol(Integer IdRol) {
        this.IdRol = IdRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoMatrno() {
        return apellidoMatrno;
    }

    public void setApellidoMatrno(String apellidoMatrno) {
        this.apellidoMatrno = apellidoMatrno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
    

    
}
