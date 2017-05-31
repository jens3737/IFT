import javax.persistence.*;

@Entity
@Table(name = "tblStageopdracht")
public class Stageopdracht {

    /////////////  Fields   ////////////////
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "OPDR_ID_categorie")
    private Categorie categorie;

    @Column(name = "OPDR_naam")
    private String naam;

    @Column(name = "OPDR_omschrijving")
    private String omschrijving;

    @Column(name = "OPDR_scope")
    private String scope;

    @Column(name = "OPDR_competenties")
    private String competenties;

    @Column(name = "OPDR_locatie")
    private String locatie;



    /////////////  Constructor   ////////////////
    public Stageopdracht() {
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

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getCompetenties() {
        return competenties;
    }

    public void setCompetenties(String competenties) {
        this.competenties = competenties;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }
}
