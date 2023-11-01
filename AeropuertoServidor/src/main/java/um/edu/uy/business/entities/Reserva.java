package um.edu.uy.business.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
        name = "Reserva"
)
public class Reserva {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id_reserva;

    public Reserva(LocalDateTime horarioInicio, LocalDateTime horarioFinalizacion, String codigoVuelo) {
        this.horarioInicio = horarioInicio;
        this.horarioFinalizacion = horarioFinalizacion;
        this.codigoVuelo = codigoVuelo;
    }

    @Column(
            nullable = false
    )
    private LocalDateTime horarioInicio;
    @Column(
            nullable = false
    )
    private LocalDateTime horarioFinalizacion;

    private String codigoVuelo;

    public Reserva() {
    }
}
