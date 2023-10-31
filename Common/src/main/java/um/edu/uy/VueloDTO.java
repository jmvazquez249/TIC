package um.edu.uy;

import java.time.LocalDateTime;
import java.util.List;

public class VueloDTO {

    private long idVuelo;

    private String codigoVuelo;

    private String codigoAeropuertoDestino;

    private String codigoAeropuertoOrigen;

    private String matriculaAvion;

    private String codigoAerolinea;

    private LocalDateTime ETA;
    private LocalDateTime EDT;



    public LocalDateTime getETA() {
        return ETA;
    }

    public void setETA(LocalDateTime ETA) {
        this.ETA = ETA;
    }

    public LocalDateTime getEDT() {
        return EDT;
    }

    public void setEDT(LocalDateTime EDT) {
        this.EDT = EDT;
    }

    public String getMatriculaAvion() {
        return matriculaAvion;
    }

    public void setMatriculaAvion(String matriculaAvion) {
        this.matriculaAvion = matriculaAvion;
    }

    public String getCodigoAerolinea() {
        return codigoAerolinea;
    }

    public void setCodigoAerolinea(String codigoAerolinea) {
        this.codigoAerolinea = codigoAerolinea;
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

    public String getCodigoAeropuertoDestino() {
        return codigoAeropuertoDestino;
    }

    public void setCodigoAeropuertoDestino(String codigoAeropuertoDestino) {
        this.codigoAeropuertoDestino = codigoAeropuertoDestino;
    }

    public String getCodigoAeropuertoOrigen() {
        return codigoAeropuertoOrigen;
    }

    public void setCodigoAeropuertoOrigen(String codigoAeropuertoOrigen) {
        this.codigoAeropuertoOrigen = codigoAeropuertoOrigen;
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
