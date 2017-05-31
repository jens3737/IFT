import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tblPersoon")
public abstract class Persoon {

    ///////////// Fields   ////////////////
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name = "Per_voornaam")
    private String voornaam;

    @Column(name = "Per_familienaam")
    private String familienaam;

    @Column(name = "Per_geboorteDatum")
    private Date geboorteDatum;

    @Column(name = "Per_email")
    private String email;

    @Column(name = "Per_geslacht")
    private byte geslacht;

    @Column (name = "Per_gsm")
    private String gsm;

    @Column (name = "Per_adres")
    private String adres;


    /////////////  Constructor   ////////////////
    public Persoon() {
        // Lege constructor
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

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public void setFamilienaam(String familienaam) {
        this.familienaam = familienaam;
    }

    public Date getGeboorteDatum() {
        return geboorteDatum;
    }

    public void setGeboorteDatum(Date geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(byte geslacht) {
        this.geslacht = geslacht;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}
