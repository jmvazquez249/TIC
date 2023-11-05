package um.edu.uy;

public class MaletasDTO {
    private long cantidadMaletas;
    private String codigoVuelo;

    private long pasaporte;

    public long getPasaporte() {
        return pasaporte;
    }

    public void setPasaporte(long pasaporte) {
        this.pasaporte = pasaporte;
    }

    public String getCodigoVuelo() {
        return codigoVuelo;
    }

    public void setCodigoVuelo(String codigoVuelo) {
        this.codigoVuelo = codigoVuelo;
    }

    public long getCantidadMaletas() {
        return cantidadMaletas;
    }

    public void setCantidadMaletas(long cantidadMaletas) {
        this.cantidadMaletas = cantidadMaletas;
    }
}
