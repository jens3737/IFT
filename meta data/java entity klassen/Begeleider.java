import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tblBegeleider")
public class Begeleider extends Persoon {

    ///////////// Fields   ////////////////
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @OneToOne
    @JoinColumn(name = "Beg_ID_persoon")
    private Persoon persoon;

    @Column(name = "Beg_functieOmschrijving")
    private String functieOmschrijving;

    @OneToMany
    @JoinColumn(name="BEGL_ID_beegeleider")
    private List<Begeleiding> begeleiding;

    /////////////  Constructor   ////////////////
    public Begeleider() {
       super();
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

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(Persoon persoon) {
        this.persoon = persoon;
    }

    public String getFunctieOmschrijving() {
        return functieOmschrijving;
    }

    public void setFunctieOmschrijving(String functieOmschrijving) {
        this.functieOmschrijving = functieOmschrijving;
    }

    public List<Begeleiding> getBegeleiding() {
        return begeleiding;
    }

    public void setBegeleiding(List<Begeleiding> begeleiding) {
        this.begeleiding = begeleiding;
    }
}
