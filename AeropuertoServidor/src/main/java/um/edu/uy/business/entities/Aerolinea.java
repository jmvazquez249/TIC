package um.edu.uy.business.entities;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Aerolinea")
@Builder

public class Aerolinea {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public long idAerolinea;

    @Column(unique = true)
    public String codigoIATAAerolinea;

    @Column
    public String nombre;


    @OneToMany(targetEntity = Avion.class,fetch = FetchType.EAGER)
    @JoinTable(
            name = "RelacionAvionAerolinea",
            joinColumns = @JoinColumn(name="id_aerolinea"),
            inverseJoinColumns = @JoinColumn(name = "id_avion")

    )
    private List<Avion> aviones;

    public List<Vuelo> getVuelos() {
        return vuelos;
    }

    @OneToMany(targetEntity = Vuelo.class)
    @JoinTable(
            name="RelacionVueloAerolinea",
            joinColumns = @JoinColumn(name = "id_aerolinea"),
            inverseJoinColumns = @JoinColumn(name = "id_vuelo")
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Vuelo> vuelos;


    public String getCodigoIATAAerolinea() {
        return codigoIATAAerolinea;
    }

    public Aerolinea(String codigoIATAAerolinea, String nombre) {
        this.codigoIATAAerolinea = codigoIATAAerolinea;
        this.nombre = nombre;
    }

    public List<Avion> getAviones() {
        return aviones;
    }



    public Aerolinea() {
    }

    public long getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(long idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public String getCodigo() {
        return codigoIATAAerolinea;
    }

    public void setCodigo(String codigoIATA) {
        this.codigoIATAAerolinea = codigoIATA;
    }

    public String getNombre() {
        return nombre;
    }
}