import javax.persistence.*;

@Entity
@Table(name = "tblCategorie")
public class Categorie {

    /////////////  Fields   ////////////////
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name = "CAT_naam")
    private String naam;


    /////////////  Constructor   ////////////////
    public Categorie() {
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
