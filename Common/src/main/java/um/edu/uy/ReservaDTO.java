package um.edu.uy;

import java.time.LocalDateTime;

public class ReservaDTO {
    private LocalDateTime localDateTimeIniPuerta;
    private LocalDateTime localDateTimeFinPuerta;
    private LocalDateTime localDateTimeIniPista;
    private LocalDateTime localDateTimeFinPista;

    private String codigoVuelo;

    private boolean llegada;

    private Long numeroPuerta;

    public Long getNumeroPuerta() {
        return numeroPuerta;
    }

    public void setNumeroPuerta(Long numeroPuerta) {
        this.numeroPuerta = numeroPuerta;
    }

    public LocalDateTime getLocalDateTimeIniPuerta() {
        return localDateTimeIniPuerta;
    }

    public void setLocalDateTimeIniPuerta(LocalDateTime localDateTimeIniPuerta) {
        this.localDateTimeIniPuerta = localDateTimeIniPuerta;
    }

    public LocalDateTime getLocalDateTimeFinPuerta() {
        return localDateTimeFinPuerta;
    }

    public void setLocalDateTimeFinPuerta(LocalDateTime localDateTimeFinPuerta) {
        this.localDateTimeFinPuerta = localDateTimeFinPuerta;
    }

    public LocalDateTime getLocalDateTimeIniPista() {
        return localDateTimeIniPista;
    }

    public void setLocalDateTimeIniPista(LocalDateTime localDateTimeIniPista) {
        this.localDateTimeIniPista = localDateTimeIniPista;
    }

    public LocalDateTime getLocalDateTimeFinPista() {
        return localDateTimeFinPista;
    }

    public void setLocalDateTimeFinPista(LocalDateTime localDateTimeFinPista) {
        this.localDateTimeFinPista = localDateTimeFinPista;
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
