package um.edu.uy.business.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Asiento")
public class Asiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAsiento;

    @Column(nullable = false)
    private int numeroAsiento;

    @Column(nullable = true)
    private long Pasaporte;
    @Column(nullable = true)
    private boolean checkIn;
    @Column(nullable = true)
    private boolean boarded;

    @OneToMany(targetEntity = Maleta.class, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "RelacionMaletaAsiento",
            joinColumns = {@JoinColumn(
                    name = "id_asiento"
            )},
            inverseJoinColumns = {@JoinColumn(
                    name = "id_maleta"
            )}
    )
    private List<Maleta> maletas;


    public Asiento(int numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    public Asiento() {

    }

    public long getIdAsiento() {
        return idAsiento;
    }

    public int getNumeroAsiento() {
        return numeroAsiento;
    }

    public long getPasaporte() {
        return Pasaporte;
    }

    public List<Maleta> getMaletas() {
        return maletas;
    }

    public void setMaletas(List<Maleta> maletas) {
        this.maletas = maletas;
    }

    public void setPasaporte(long pasaporte) {
        Pasaporte = pasaporte;
    }
}
