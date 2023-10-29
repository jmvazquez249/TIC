package um.edu.uy;

public class AerolineaDTO {
    private long idAerolinea;
    private String codigoIATAAerolinea;


    public AerolineaDTO() {
    }

    private String nombre;
    private String PaisAero;
    private String codigoICAO;

    public long getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(long idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public String getCodigoIATAAerolinea() {
        return codigoIATAAerolinea;
    }

    public void setCodigoIATAAerolinea(String codigoIATAAerolinea) {
        this.codigoIATAAerolinea = codigoIATAAerolinea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaisAero() {
        return PaisAero;
    }

    public void setPaisAero(String paisAero) {
        PaisAero = paisAero;
    }

    public String getCodigoICAO() {
        return codigoICAO;
    }

    public void setCodigoICAO(String codigoICAO) {
        this.codigoICAO = codigoICAO;
    }
}
