package desktop.modelo.pojo;

public class Envio {
    private Integer idEnvio;
    private String numeroGuia;
    private String costo; 
    private Integer idColaborador;
    private Integer idCliente;
    private Integer idEstatus;
    private String calleOrigen;
    private String numeroOrigen;
    private String coloniaOrigen;
    private String codigoPostalOrigen;
    private String estadoOrigen;
    private String ciudadOrigen;
    private String calleDestino;
    private String numeroDestino;
    private String coloniaDestino;
    private String codigoPostalDestino;
    private String estadoDestino;
    private String ciudadDestino;

    // Constructor por defecto
    public Envio() {}

    // Constructor con todos los campos
    public Envio(Integer idEnvio, String numeroGuia, String costo, Integer idColaborador, Integer idCliente, Integer idEstatus,
                 String calleOrigen, String numeroOrigen, String coloniaOrigen, String codigoPostalOrigen, String estadoOrigen, String ciudadOrigen,
                 String calleDestino, String numeroDestino, String coloniaDestino, String codigoPostalDestino, String estadoDestino, String ciudadDestino) {
        this.idEnvio = idEnvio;
        this.numeroGuia = numeroGuia;
        this.costo = costo;
        this.idColaborador = idColaborador;
        this.idCliente = idCliente;
        this.idEstatus = idEstatus;
        this.calleOrigen = calleOrigen;
        this.numeroOrigen = numeroOrigen;
        this.coloniaOrigen = coloniaOrigen;
        this.codigoPostalOrigen = codigoPostalOrigen;
        this.estadoOrigen = estadoOrigen;
        this.ciudadOrigen = ciudadOrigen;
        this.calleDestino = calleDestino;
        this.numeroDestino = numeroDestino;
        this.coloniaDestino = coloniaDestino;
        this.codigoPostalDestino = codigoPostalDestino;
        this.estadoDestino = estadoDestino;
        this.ciudadDestino = ciudadDestino;
    }

    // Métodos getter y setter para cada atributo

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
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

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getCalleOrigen() {
        return calleOrigen;
    }

    public void setCalleOrigen(String calleOrigen) {
        this.calleOrigen = calleOrigen;
    }

    public String getNumeroOrigen() {
        return numeroOrigen;
    }

    public void setNumeroOrigen(String numeroOrigen) {
        this.numeroOrigen = numeroOrigen;
    }

    public String getColoniaOrigen() {
        return coloniaOrigen;
    }

    public void setColoniaOrigen(String coloniaOrigen) {
        this.coloniaOrigen = coloniaOrigen;
    }

    public String getCodigoPostalOrigen() {
        return codigoPostalOrigen;
    }

    public void setCodigoPostalOrigen(String codigoPostalOrigen) {
        this.codigoPostalOrigen = codigoPostalOrigen;
    }

    public String getEstadoOrigen() {
        return estadoOrigen;
    }

    public void setEstadoOrigen(String estadoOrigen) {
        this.estadoOrigen = estadoOrigen;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public String getCalleDestino() {
        return calleDestino;
    }

    public void setCalleDestino(String calleDestino) {
        this.calleDestino = calleDestino;
    }

    public String getNumeroDestino() {
        return numeroDestino;
    }

    public void setNumeroDestino(String numeroDestino) {
        this.numeroDestino = numeroDestino;
    }

    public String getColoniaDestino() {
        return coloniaDestino;
    }

    public void setColoniaDestino(String coloniaDestino) {
        this.coloniaDestino = coloniaDestino;
    }

    public String getCodigoPostalDestino() {
        return codigoPostalDestino;
    }

    public void setCodigoPostalDestino(String codigoPostalDestino) {
        this.codigoPostalDestino = codigoPostalDestino;
    }

    public String getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(String estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    // Método toString (opcional)
    @Override
    public String toString() {
        return calleOrigen + " - " + ciudadOrigen + " / " + calleDestino + " - " + ciudadDestino;
    }
}
