package um.edu.uy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class VueloDTO {

    private long idVuelo;

    private String codigoVuelo;

    private String codigoAeropuertoDestino;

    private String codigoAeropuertoOrigen;

    private String matriculaAvion;

    private String codigoAerolinea;


    private LocalDate fechaETA;

    private LocalTime horaETA;

    private LocalDate fechaEDT;

    private LocalTime horaEDT;

    private LocalDateTime ETA;

    private LocalDateTime EDT;
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

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

    public LocalDate getFechaETA() {
        return fechaETA;
    }

    public void setFechaETA(LocalDate fechaETA) {
        this.fechaETA = fechaETA;
    }

    public LocalTime getHoraETA() {
        return horaETA;
    }

    public void setHoraETA(LocalTime horaETA) {
        this.horaETA = horaETA;
    }

    public LocalDate getFechaEDT() {
        return fechaEDT;
    }

    public void setFechaEDT(LocalDate fechaEDT) {
        this.fechaEDT = fechaEDT;
    }

    public LocalTime getHoraEDT() {
        return horaEDT;
    }

    public void setHoraEDT(LocalTime horaEDT) {
        this.horaEDT = horaEDT;
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
