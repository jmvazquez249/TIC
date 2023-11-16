package um.edu.uy.business.entities;

import javax.persistence.*;

@Entity
@Table(name = "Maleta")
public class Maleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMaleta;

    @Column(nullable = true)
    private boolean subidoAvion;

    @Column(nullable = true)
    private boolean entregadoCliente;

    public long getIdMaleta() {
        return idMaleta;
    }

    public void setIdMaleta(long idMaleta) {
        this.idMaleta = idMaleta;
    }

    public boolean isSubidoAvion() {
        return subidoAvion;
    }

    public void setSubidoAvion(boolean subidoAvion) {
        this.subidoAvion = subidoAvion;
    }

    public boolean isEntregadoCliente() {
        return entregadoCliente;
    }

    public void setEntregadoCliente(boolean entregadoCliente) {
        this.entregadoCliente = entregadoCliente;
    }
}
