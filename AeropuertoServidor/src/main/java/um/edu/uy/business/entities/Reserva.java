package um.edu.uy.business.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_reserva;

    public Reserva(LocalTime horarioInicio, LocalTime horarioFinalizacion, LocalDate fecha, String codigoVuelo) {
        this.horarioInicio = horarioInicio;
        this.horarioFinalizacion = horarioFinalizacion;
        this.codigoVuelo = codigoVuelo;
        this.fecha=fecha;
    }


    @Column(nullable = false)
    private LocalDate fecha;

    @Column(
            nullable = false
    )
    private LocalTime horarioInicio;
    @Column(
            nullable = false
    )
    private LocalTime horarioFinalizacion;

    private String codigoVuelo;

    public Reserva() {
    }
}
