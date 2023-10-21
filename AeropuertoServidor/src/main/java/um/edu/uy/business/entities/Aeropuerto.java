package um.edu.uy.business.entities;

import org.springframework.data.jpa.repository.Query;

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




}
