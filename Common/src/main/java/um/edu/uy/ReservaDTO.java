package um.edu.uy;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservaDTO {
    private LocalTime localTimeIniPuerta;
    private LocalTime localTimeFinPuerta;
    private LocalTime localTimeIniPista;
    private LocalTime localTimeFinPista;

    private String codigoVuelo;

    private boolean llegada;

    private Long numeroPuerta;

    public Long getNumeroPuerta() {
        return numeroPuerta;
    }

    public void setNumeroPuerta(Long numeroPuerta) {
        this.numeroPuerta = numeroPuerta;
    }

    public LocalTime getLocalTimeIniPuerta() {
        return localTimeIniPuerta;
    }

    public void setLocalTimeIniPuerta(LocalTime localTimeIniPuerta) {
        this.localTimeIniPuerta = localTimeIniPuerta;
    }

    public LocalTime getLocalTimeFinPuerta() {
        return localTimeFinPuerta;
    }

    public void setLocalTimeFinPuerta(LocalTime localTimeFinPuerta) {
        this.localTimeFinPuerta = localTimeFinPuerta;
    }

    public LocalTime getLocalTimeIniPista() {
        return localTimeIniPista;
    }

    public void setLocalTimeIniPista(LocalTime localTimeIniPista) {
        this.localTimeIniPista = localTimeIniPista;
    }

    public LocalTime getLocalTimeFinPista() {
        return localTimeFinPista;
    }

    public void setLocalTimeFinPista(LocalTime localTimeFinPista) {
        this.localTimeFinPista = localTimeFinPista;
    }

    public String getCodigoVuelo() {
        return codigoVuelo;
    }

    public void setCodigoVuelo(String codigoVuelo) {
        this.codigoVuelo = codigoVuelo;
    }

    public boolean isLlegada() {
        return llegada;
    }

    public void setLlegada(boolean llegada) {
        this.llegada = llegada;
    }
}
