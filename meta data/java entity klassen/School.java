import javax.persistence.*;

@Entity
@Table(name = "tblSchool")
public class School {
    ///////////// Fields   ////////////////
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name = "Scho_adres")
    private String adres;

    @Column(name = "Scho_naam")
    private String naam;

    @Column(name = "Scho_contactpersoon")
    private String contactpersoon;

    @Column(name = "Scho_contactnummer")
    private String contactnummer;



    /////////////  Constructor   ////////////////
    public School() {
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

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getContactpersoon() {
        return contactpersoon;
    }

    public void setContactpersoon(String contactpersoon) {
        this.contactpersoon = contactpersoon;
    }

    public String getContactnummer() {
        return contactnummer;
    }

    public void setContactnummer(String contactnummer) {
        this.contactnummer = contactnummer;
    }
}


