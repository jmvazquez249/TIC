package um.edu.uy;

import java.time.LocalTime;

public class VueloReservaDTO {

    private String codigoVuelo;
    private Long numeroPuerta;
    private LocalTime horaInicioPuerta;
    private LocalTime horaFinPuerta;
    private LocalTime horaInicioPista;
    private LocalTime horaFinPista;



    public String getCodigoVuelo() {
        return codigoVuelo;
    }

    public void setCodigoVuelo(String codigoVuelo) {
        this.codigoVuelo = codigoVuelo;
    }

    public Long getNumeroPuerta() {
        return numeroPuerta;
    }

    public void setNumeroPuerta(Long numeroPuerta) {
        this.numeroPuerta = numeroPuerta;
    }

    public LocalTime getHoraInicioPuerta() {
        return horaInicioPuerta;
    }

    public void setHoraInicioPuerta(LocalTime horaInicioPuerta) {
        this.horaInicioPuerta = horaInicioPuerta;
    }

    public LocalTime getHoraFinPuerta() {
        return horaFinPuerta;
    }

    public void setHoraFinPuerta(LocalTime horaFinPuerta) {
        this.horaFinPuerta = horaFinPuerta;
    }

    public LocalTime getHoraInicioPista() {
        return horaInicioPista;
    }

    public void setHoraInicioPista(LocalTime horaInicioPista) {
        this.horaInicioPista = horaInicioPista;
    }

    public LocalTime getHoraFinPista() {
        return horaFinPista;
    }

    public void setHoraFinPista(LocalTime horaFinPista) {
        this.horaFinPista = horaFinPista;
    }
}
