package um.edu.uy.business.entities;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="Avion")
public class Avion {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id_avion;

    @Column(unique = true)
    private String matricula;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private int capacidad;

    @Column(nullable = false)
    private int capacidadBulto;

    public String getMatricula() {
        return matricula;
    }

    public Avion() {
    }

    public Avion(String matricula, String modelo, int capacidad, int capacidadBulto) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.capacidad = capacidad;
        this.capacidadBulto = capacidadBulto;
    }

    public long getId_avion() {
        return id_avion;
    }

    public String getModelo() {
        return modelo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getCapacidadBulto() {
        return capacidadBulto;
    }
}
