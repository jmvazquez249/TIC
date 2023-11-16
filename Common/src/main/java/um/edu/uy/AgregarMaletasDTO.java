package um.edu.uy;

public class AgregarMaletasDTO {
    private String codigoVueloMaletero;
    private String codigoAeropuertoMaletero;
    private long idMaleta;

    public AgregarMaletasDTO() {
    }
    public long getIdMaleta() {
        return idMaleta;
    }
    public void setIdMaleta(long idMaleta) {
        this.idMaleta = idMaleta;
    }

    public String getCodigoVueloMaletero() {
        return codigoVueloMaletero;
    }
    public void setCodigoVueloMaletero(String codigoVueloMaletero) {
        this.codigoVueloMaletero = codigoVueloMaletero;
    }
    public String getCodigoAeropuertoMaletero() {
        return codigoAeropuertoMaletero;
    }
    public void setCodigoAeropuertoMaletero(String codigoAeropuertoMaletero) {
        this.codigoAeropuertoMaletero = codigoAeropuertoMaletero;
    }


}
