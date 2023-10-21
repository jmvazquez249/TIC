package um.edu.uy;

public class AeropuertoDTO {

    private long idAeropuerto;

    private String codigoIATAAeropuerto;

    private String nombre;

    private String ciudad;

    private String pais;

    public AeropuertoDTO() {
    }

    public long getIdAeropuerto() {
        return idAeropuerto;
    }

    public void setIdAeropuerto(long idAeropuerto) {
        this.idAeropuerto = idAeropuerto;
    }

    public String getCodigoIATAAeropuerto() {
        return codigoIATAAeropuerto;
    }

    public void setCodigoIATAAeropuerto(String codigoIATAAeropuerto) {
        this.codigoIATAAeropuerto = codigoIATAAeropuerto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
