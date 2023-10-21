package um.edu.uy;

public class VueloDTO {

    private long idVuelo;

    private String codigoVuelo;

    private long idAeropuertoDestino;

    private long idAeropuertoOrigen;

    private long idAvion;

    private long idAerolinea;

    public long getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(long idAvion) {
        this.idAvion = idAvion;
    }

    public long getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(long idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    private boolean aceptadoOrigen;

    private boolean acepradoDestino;

    private boolean rechadado;

    public VueloDTO() {
    }

    public long getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(long idVuelo) {
        this.idVuelo = idVuelo;
    }

    public String getCodigoVuelo() {
        return codigoVuelo;
    }

    public void setCodigoVuelo(String codigoVuelo) {
        this.codigoVuelo = codigoVuelo;
    }

    public long getIdAeropuertoDestino() {
        return idAeropuertoDestino;
    }

    public void setIdAeropuertoDestino(long idAeropuertoDestino) {
        this.idAeropuertoDestino = idAeropuertoDestino;
    }

    public long getIdAeropuertoOrigen() {
        return idAeropuertoOrigen;
    }

    public void setIdAeropuertoOrigen(long idAeropuertoOrigen) {
        this.idAeropuertoOrigen = idAeropuertoOrigen;
    }

    public boolean isAceptadoOrigen() {
        return aceptadoOrigen;
    }

    public void setAceptadoOrigen(boolean aceptadoOrigen) {
        this.aceptadoOrigen = aceptadoOrigen;
    }

    public boolean isAcepradoDestino() {
        return acepradoDestino;
    }

    public void setAcepradoDestino(boolean acepradoDestino) {
        this.acepradoDestino = acepradoDestino;
    }

    public boolean isRechadado() {
        return rechadado;
    }

    public void setRechadado(boolean rechadado) {
        this.rechadado = rechadado;
    }
}
