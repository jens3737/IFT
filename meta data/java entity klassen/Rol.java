import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tblRol")
public class Rol {

    ///////////// Fields   ////////////////
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name = "Rol_naam")
    private String naam;

    @OneToMany
    @JoinColumn(name = "RPE_ID_rol")
    private List<RolPermission> rolPermission;

    /////////////  Constructor   ////////////////
    public Rol() {
        // Lege constructor
    }

    /////////////  TO STRING  /////////////
    @Override
    public String toString() {
        // To Be Continued
        return null;
    }


    ////////  Getters & Setters //////////
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
}
