package um.edu.uy.business.entities;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table
public class UsuarioGeneral {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long numeroUsuario;
    @Column(unique = true, length = 9)
    private long pasaporte;
    @Column( length = 20,nullable = false)
    private String nombre;
    @Column( length = 20,nullable = false)
    private String apellido;
    @Column(length = 20, nullable = false)
    private String contrasena;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String tipo;

    @OneToOne(targetEntity = Aerolinea.class)
    @JoinColumn(name = "id_aerolinea")
    private Aerolinea aerolinea;

    @OneToOne(targetEntity = Aeropuerto.class)
    @JoinColumn(name = "id_aeropuerto")
    private Aeropuerto aeropuerto;

    public String getTipo() {
        return tipo;
    }

    public UsuarioGeneral(long pasaporte, String nombre, String apellido, String contrasena, String email, String tipo, Aerolinea aerolinea, Aeropuerto aeropuerto) {
        this.pasaporte = pasaporte;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.email = email;
        this.tipo = tipo;
        this.aerolinea = aerolinea;
        this.aeropuerto = aeropuerto;
    }

    public UsuarioGeneral() {
    }



    public long getNumeroUsuario() {
        return numeroUsuario;
    }

    public long getPasaporte() {
        return pasaporte;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getEmail() {
        return email;
    }

    public Aerolinea getAerolinea() {
        return aerolinea;
    }
    public Aeropuerto getAeropuerto(){return aeropuerto;}

    public String getCodigoAerolinea(){
        if (aerolinea==null){
            return null;
        }
        return aerolinea.getCodigoIATAAerolinea();}

    public String getCodigoAeropuerto(){
        if (aeropuerto==null){
            return null;
        }
        return aeropuerto.getCodigoIATAAeropuerto();}

    public void setNumeroUsuario(long numeroUsuario) {
        this.numeroUsuario = numeroUsuario;
    }

    public void setPasaporte(long pasaporte) {
        this.pasaporte = pasaporte;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setAerolinea(Aerolinea aerolinea) {
        this.aerolinea = aerolinea;
    }

    public void setAeropuerto(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
    }
}
