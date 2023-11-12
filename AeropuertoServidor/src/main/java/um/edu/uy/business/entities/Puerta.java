package um.edu.uy.business.entities;


import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "Puerta")
public class Puerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPuerta;

    public Puerta() {
    }
    public long getIdPuerta() {
        return idPuerta;
    }
}
