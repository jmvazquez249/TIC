package um.edu.uy;

import java.util.List;

public class AeropuertoDTO {

    private long idAeropuerto;

    private String codigoIATAAeropuerto;

    private String nombre;

    private String ciudad;

    private String pais;

    private long cantidadPuertas;

    public long getCantidadPuertas() {
        return cantidadPuertas;
    }

    public void setCantidadPuertas(long cantidadPuertas) {
        this.cantidadPuertas = cantidadPuertas;
    }

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

    private List<Long> puertas;

    public List<Long> getPuertas() {
        return puertas;
    }

    public void setPuertas(List<Long> puertas) {
        this.puertas = puertas;
    }
}
