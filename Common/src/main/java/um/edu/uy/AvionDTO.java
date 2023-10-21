package um.edu.uy;

public class AvionDTO {
    private long idAvion;

    private String matricula;

    private String modelo;

    private int capacidad;

    private String codigoAeroliena;

    public void setCodigoAeroliena(String codigoAeroliena) {
        this.codigoAeroliena = codigoAeroliena;
    }

    public String getCodigoAeroliena() {
        return codigoAeroliena;
    }

    public AvionDTO() {
    }

    public long getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(long idAvion) {
        this.idAvion = idAvion;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}
