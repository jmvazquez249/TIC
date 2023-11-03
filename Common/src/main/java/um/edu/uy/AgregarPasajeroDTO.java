package um.edu.uy;

public class AgregarPasajeroDTO {
    private String codigoVuelo;
    private long pasaporte;


    public void setCodigoVuelo(String codigoVuelo) {
        this.codigoVuelo = codigoVuelo;
    }

    public void setPasaporte(long pasaporte) {
        this.pasaporte = pasaporte;
    }

    public AgregarPasajeroDTO() {

    }

    public String getCodigoVuelo() {
        return codigoVuelo;
    }

    public long getPasaporte() {
        return pasaporte;
    }


}
