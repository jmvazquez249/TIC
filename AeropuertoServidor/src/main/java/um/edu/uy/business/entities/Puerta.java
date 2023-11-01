package um.edu.uy.business.entities;


import java.util.List;
import javax.persistence.*;

@Entity
@Table(
        name = "Puerta"
)
public class Puerta {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long idPuerta;
    @OneToMany(
            targetEntity = Reserva.class
    )
    @JoinTable(
            name = "RelacionPuertaReserva",
            joinColumns = {@JoinColumn(
                    name = "id_puerta"
            )},
            inverseJoinColumns = {@JoinColumn(
                    name = "id_reserva"
            )}
    )
    private List<Reserva> reservasPuerta;

    public Puerta() {
    }

    public List<Reserva> getReservasPuerta() {
        return this.reservasPuerta;
    }

    public long getIdPuerta() {
        return idPuerta;
    }
}
