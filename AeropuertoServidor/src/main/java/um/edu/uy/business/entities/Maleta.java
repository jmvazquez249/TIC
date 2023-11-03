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
}
