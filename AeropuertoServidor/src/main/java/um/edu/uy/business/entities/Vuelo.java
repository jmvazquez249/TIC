package um.edu.uy.business.entities;



import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Vuelos")
public class Vuelo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id_vuelo;

    @Column(nullable = false, unique = true)
    private String codigoVuelo;

    @OneToOne(targetEntity = Aeropuerto.class)
    @JoinColumn(name = "AeropuertoDestino")
    private Aeropuerto aeropuertoDestino;

    @OneToOne(targetEntity = Aeropuerto.class)
    @JoinColumn(name = "AeropertoOrigen")
    private Aeropuerto aeropuertoOrigen;

    public long getId_vuelo() {
        return id_vuelo;
    }

    @OneToOne(targetEntity = Avion.class)
    @JoinColumn(name = "id_avion")
    private Avion avion;

    @OneToOne(targetEntity = Aerolinea.class)
    @JoinColumn(name="id_aerolinea")
    private Aerolinea aerolinea;

    @Column(nullable = false)
    private boolean aceptadoOrigen;

    @Column(nullable = false)
    private boolean aceptadoDestino;

    @Column(nullable = false)
    private boolean rechadado;

    @Column(nullable = false)
    private LocalDateTime EDT;

    @Column(nullable = false)
    private LocalDateTime ETA;

    public String getAerolinea() {
        return aerolinea.getCodigoIATAAerolinea();
    }

    public void setRechadado(boolean rechadado) {
        this.rechadado = rechadado;
    }

    public void setAceptadoDestino(boolean aceptadoDestino) {
        this.aceptadoDestino = aceptadoDestino;
    }

    public Vuelo() {
    }

    public String getAvion(){
        return this.avion.getMatricula();
    }

    public String getCodigoVuelo() {
        return codigoVuelo;
    }

    public String getAeropuertoDestino() {
        return aeropuertoDestino.getCodigoIATAAeropuerto();
    }

    public String getAeropuertoOrigen() {
        return aeropuertoOrigen.getCodigoIATAAeropuerto();
    }

    public void setAceptadoOrigen(boolean aceptadoOrigen) {
        this.aceptadoOrigen = aceptadoOrigen;
    }

    public Vuelo(String codigoVuelo, Aeropuerto aeropuertoDestino, Aeropuerto aeropuertoOrigen, Avion avion, Aerolinea aerolinea, boolean aceptadoOrigen, boolean aceptadoDestino, LocalDateTime EDT, LocalDateTime ETA) {
        this.codigoVuelo = codigoVuelo;
        this.aeropuertoDestino = aeropuertoDestino;
        this.aeropuertoOrigen = aeropuertoOrigen;
        this.avion = avion;
        this.aerolinea = aerolinea;
        this.aceptadoOrigen = aceptadoOrigen;
        this.aceptadoDestino = aceptadoDestino;
        this.EDT = EDT;
        this.ETA = ETA;
    }

    public boolean isAceptadoOrigen() {
        return aceptadoOrigen;
    }

    public boolean isAceptadoDestino() {
        return aceptadoDestino;
    }

    public LocalDateTime getEDT() {
        return EDT;
    }

    public LocalDateTime getETA() {
        return ETA;
    }
}
