package um.edu.uy.business.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.*;

@Entity
@Table(name = "Reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_reserva;

    public Pista getPista() {
        return pista;
    }

    public void setPista(Pista pista) {
        this.pista = pista;
    }

    public Puerta getPuerta() {
        return puerta;
    }

    public void setPuerta(Puerta puerta) {
        this.puerta = puerta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicioPuerta() {
        return horaInicioPuerta;
    }

    public void setHoraInicioPuerta(LocalTime horaInicioPuerta) {
        this.horaInicioPuerta = horaInicioPuerta;
    }

    public LocalTime getHoraFinalizacionPuerta() {
        return horaFinalizacionPuerta;
    }

    public void setHoraFinalizacionPuerta(LocalTime horaFinalizacionPuerta) {
        this.horaFinalizacionPuerta = horaFinalizacionPuerta;
    }

    public LocalTime getHoraInicioPista() {
        return horaInicioPista;
    }

    public void setHoraInicioPista(LocalTime horaInicioPista) {
        this.horaInicioPista = horaInicioPista;
    }

    public LocalTime getHoraFinalizacionPista() {
        return horaFinalizacionPista;
    }

    public void setHoraFinalizacionPista(LocalTime horaFinalizacionPista) {
        this.horaFinalizacionPista = horaFinalizacionPista;
    }

    @OneToOne(targetEntity = Pista.class)
    @JoinColumn(name = "id_pista")
    private Pista pista;
    @OneToOne(targetEntity = Puerta.class)
    @JoinColumn(name = "id_puerta")
    private Puerta puerta;
    @Column(nullable = false)
    private LocalDate fecha;
    @Column(nullable = false)
    private LocalTime horaInicioPuerta;
    @Column(nullable = false)
    private LocalTime horaFinalizacionPuerta;
    @Column(nullable = false)
    private LocalTime horaInicioPista;
    @Column(nullable = false)
    private LocalTime horaFinalizacionPista;


    public Reserva(Pista pista, Puerta puerta, LocalDate fecha, LocalTime horaInicioPuerta, LocalTime horaFinalizacionPuerta, LocalTime horaInicioPista, LocalTime horaFinalizacionPista) {
        this.pista = pista;
        this.puerta = puerta;
        this.fecha = fecha;
        this.horaInicioPuerta = horaInicioPuerta;
        this.horaFinalizacionPuerta = horaFinalizacionPuerta;
        this.horaInicioPista = horaInicioPista;
        this.horaFinalizacionPista = horaFinalizacionPista;
    }
    public Reserva() {
    }
}
