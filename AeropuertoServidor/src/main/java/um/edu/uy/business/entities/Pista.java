package um.edu.uy.business.entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Pista")
public class Pista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_pista;

    public Pista() {
    }

    public long getId_pista() {
        return id_pista;
    }
}
