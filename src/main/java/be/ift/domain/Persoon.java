package be.ift.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "tblpersoon")
public  class Persoon {

    ///////////// Fields   ////////////////
    @Id
    @Column(name = "PER_ID")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "Per_voornaam_naam")
    @NotNull
    private String naam;

    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d-M-YYYY")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Per_geboortedatum")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date geboorteDatum;

    @Column(name = "Per_email")
//    @Email
    private String email;

    @Column(name = "Per_geslacht")
    private boolean geslacht;

    @Column (name = "Per_gsmnummer")
    @Size(min = 3, max = 20)
    private String gsm;

    @Column (name = "Per_adres")
    private String adres;


    /////////////  Constructor   ////////////////
    public Persoon() {
        // Lege constructor
        // Lege constructor
    }

    public Persoon(String naam, Date geboorteDatum, String email, boolean geslacht, String gsm, String adres) {
        this.naam = naam;
        this.geboorteDatum = geboorteDatum;
        this.email = email;
        this.geslacht = geslacht;
        this.gsm = gsm;
        this.adres = adres;
    }

    public Persoon(String naam, String email, String gsm) {
        this.naam = naam;
        this.email = email;
        this.gsm = gsm;
    }

    /////////////  TO STRING  /////////////
    @Override
    public String toString() {
        // To Be Continued
        return null;
    }


    ////////  Getters & Setters //////////

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getNaam() {
        return naam;
    }

    public void setNaam(String voornaam) {
        this.naam = voornaam;
    }

    public Date getGeboorteDatum() {
        return geboorteDatum;
    }

    public void setGeboorteDatum(Date geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public boolean isGeslacht() {
        return geslacht;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(boolean geslacht) {
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
