import javax.persistence.*;

@Entity
@Table(name = "tblEvaluatietemplate")
public class Evaluatietemplate {

    /////////////  Fields   ////////////////
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name = "TEMP_naam")
    private String naam;

    /////////////  Constructor   ////////////////
    public Evaluatietemplate() {
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

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
}
