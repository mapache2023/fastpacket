/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.modelo.pojo;

/**
 *
 * @author mapache
 */
public class Colaborador {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String curp;
    private String numeroLicencia;
    private String numeroPesonal;
    private Integer idColaborador;
    private Integer idUnidad;
    private String unidad;
    private Integer idEnvio;
    private Integer idRol;
    private String rol;
    private byte [] fotografia;
    
       public Colaborador(){}

    public Colaborador(String nombre, String apellidoPaterno, String apellidoMaterno, String correo, String curp, String numeroLicencia, String numeroPesonal, Integer idColaborador, Integer idUnidad, String unidad, Integer idEnvio, Integer idRol, String rol, byte[] fotografia) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.curp = curp;
        this.numeroLicencia = numeroLicencia;
        this.numeroPesonal = numeroPesonal;
        this.idColaborador = idColaborador;
        this.idUnidad = idUnidad;
        this.unidad = unidad;
        this.idEnvio = idEnvio;
        this.idRol = idRol;
        this.rol = rol;
        this.fotografia = fotografia;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public String getNumeroPesonal() {
        return numeroPesonal;
    }

    public void setNumeroPesonal(String numeroPesonal) {
        this.numeroPesonal = numeroPesonal;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
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

    public byte[] getFotografia() {
        return fotografia;
    }

    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }
       
   
    
}
