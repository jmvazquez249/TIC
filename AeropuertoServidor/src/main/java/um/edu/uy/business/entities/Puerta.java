package um.edu.uy.business.entities;


import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(
        name = "Puerta"
)
public class Puerta {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id_puerta;
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

    public long getId_puerta() {
        return id_puerta;
    }
}
