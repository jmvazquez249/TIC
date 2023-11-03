package um.edu.uy;

public class AgregarPasajeroDTO {
    private String codigoVuelo;
    private long pasaporte;


    public AgregarPasajeroDTO(String codigoVuelo, long pasaporte) {
        this.codigoVuelo = codigoVuelo;
        this.pasaporte = pasaporte;

    }

    public String getCodigoVuelo() {
        return codigoVuelo;
    }

    public long getPasaporte() {
        return pasaporte;
    }


}
