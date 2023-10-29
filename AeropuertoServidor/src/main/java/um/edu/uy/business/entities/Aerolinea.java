package um.edu.uy.business.entities;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.validation.constraints.Pattern;



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

    @Column(unique = true,nullable = false, length = 2)
    public String codigoIATAAerolinea;

    @Column(nullable = false)
    public String nombre;
    @Column(nullable = false)
    public String PaisAero;
    @Column(unique = true,nullable = false, length = 3, columnDefinition = "text")
    public String codigoICAO;


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

    public Aerolinea(String codigoIATAAerolinea, String nombre, String paisAero, String codigoICAO) {
        this.codigoIATAAerolinea = codigoIATAAerolinea;
        this.nombre = nombre;
        this.PaisAero = paisAero;
        this.codigoICAO = codigoICAO;
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

    public String getPaisAero() {
        return PaisAero;
    }

    public void setPaisAero(String paisAero) {
        PaisAero = paisAero;
    }

    public String getCodigoICAO() {
        return codigoICAO;
    }

    public void setCodigoICAO(String codigoICAO) {
        this.codigoICAO = codigoICAO;
    }
}