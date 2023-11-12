package um.edu.uy.business.entities;



import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
    private LocalDate fechaETA;

    @Column(nullable = false)
    private LocalTime horaETA;

    @Column(nullable = false)
    private LocalDate fechaEDT;

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

    @Column(nullable = false)
    private LocalTime horaEDT;





    @OneToMany(targetEntity = Asiento.class, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "RelacionAsientoVuelo",
            joinColumns = {@JoinColumn(
                    name = "id_vuelo"
            )},
            inverseJoinColumns = {@JoinColumn(
                    name = "id_asiento"
            )}
    )
    private List<Asiento> asientos;

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

    public Vuelo(String codigoVuelo, Aeropuerto aeropuertoDestino, Aeropuerto aeropuertoOrigen, Avion avion, Aerolinea aerolinea, boolean aceptadoOrigen, boolean aceptadoDestino, LocalDate fechaETA, LocalTime horaETA, LocalDate fechaEDT, LocalTime horaEDT) {
        this.codigoVuelo = codigoVuelo;
        this.aeropuertoDestino = aeropuertoDestino;
        this.aeropuertoOrigen = aeropuertoOrigen;
        this.avion = avion;
        this.aerolinea = aerolinea;
        this.aceptadoOrigen = aceptadoOrigen;
        this.aceptadoDestino = aceptadoDestino;
        this.fechaETA = fechaETA;
        this.horaETA = horaETA;
        this.fechaEDT = fechaEDT;
        this.horaEDT = horaEDT;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

    public boolean isAceptadoOrigen() {
        return aceptadoOrigen;
    }

    public boolean isAceptadoDestino() {
        return aceptadoDestino;
    }

    public LocalDateTime getEDT(){
        return LocalDateTime.of(this.getFechaEDT(),this.getHoraEDT());
    }

    public LocalDateTime getETA(){
        return LocalDateTime.of(this.getFechaETA(),this.getHoraETA());
    }




}
