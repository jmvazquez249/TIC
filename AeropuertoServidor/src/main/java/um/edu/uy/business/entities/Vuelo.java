package um.edu.uy.business.entities;


import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Vuelos")
public class Vuelo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id_vuelo;

    @Column(nullable = false)
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

    public Aeropuerto getAeropuertoDestino() {
        return aeropuertoDestino;
    }

    public Aeropuerto getAeropuertoOrigen() {
        return aeropuertoOrigen;
    }

    public void setAceptadoOrigen(boolean aceptadoOrigen) {
        this.aceptadoOrigen = aceptadoOrigen;
    }

    public Vuelo(String codigoVuelo, Aeropuerto aeropuertoDestino, Aeropuerto aeropuertoOrigen, Avion avion, Aerolinea aerolinea, boolean aceptadoOrigen, boolean aceptadoDestino) {
        this.codigoVuelo = codigoVuelo;
        this.aeropuertoDestino = aeropuertoDestino;
        this.aeropuertoOrigen = aeropuertoOrigen;
        this.avion = avion;
        this.aerolinea = aerolinea;
        this.aceptadoOrigen = aceptadoOrigen;
        this.aceptadoDestino = aceptadoDestino;
    }


}
