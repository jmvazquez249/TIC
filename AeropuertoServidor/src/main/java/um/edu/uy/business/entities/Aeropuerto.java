package um.edu.uy.business.entities;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="Aeropuerto")
public class Aeropuerto {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long idAeropuerto;

    @Column(unique = true,name = "codigoIATAAeropuerto")
    private String codigoIATAAeropuerto;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String ciudad;

    public long getIdAeropuerto() {
        return idAeropuerto;
    }

    @Column(nullable = false)
    private String pais;

    @OneToMany(targetEntity = Puerta.class, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "RelacionPuertaAeropuerto",
            joinColumns = {@JoinColumn(
                    name = "id_aeropuerto"
            )},
            inverseJoinColumns = {@JoinColumn(
                    name = "id_puerta"
            )}
    )
    private List<Puerta> puertas;
    @OneToOne(
            targetEntity = Pista.class,
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(
            name = "id_pista"
    )
    private Pista pista;





    public Aeropuerto() {
    }

    public Aeropuerto(String codigoIATAAeropuerto, String nombre, String ciudad, String pais) {
        this.codigoIATAAeropuerto = codigoIATAAeropuerto;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.pais = pais;
    }

    public String getCodigoIATAAeropuerto() {
        return codigoIATAAeropuerto;
    }

    @Override
    public String toString(){
        return this.codigoIATAAeropuerto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPuertas(List<Puerta> puertas) {
        this.puertas = puertas;
    }

    public void setPista(Pista pista) {
        this.pista = pista;
    }
}
