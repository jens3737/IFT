package be.ift.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tblschool")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class School {
    ///////////// Fields   ////////////////
    @Id
    @Column(name = "Scho_ID")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "Scho_adres")
    private String adres;

    @Column(name = "Scho_naam")
    private String naam;

    @OneToOne
    @JoinColumn(name = "Scho_ID_contactpersoon")
    private Persoon contactpersoon;

    @Column(name = "Scho_contactnummer")
    private String contactnummer;

    @OneToMany
    @JoinColumn(name = "STA_ID_school")
//    @JsonManagedReference
    @JsonBackReference
    private List<Stagiair> stagiair;

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Stagiair> getStagiair() {
        return stagiair;
    }

    public void setStagiair(List<Stagiair> stagiair) {
        this.stagiair = stagiair;
    }

    /////////////  Constructor   ////////////////
    public School() {
        // Lege constructor
    }

    public School(String naam) {
        this.naam = naam;
    }

    public School(Integer id, String naam) {
        this.naam = naam;
        this.id = id;
    }

    public School(String adres, String naam, Persoon contactpersoon, String contactnummer) {
        this.adres = adres;
        this.naam = naam;
        this.contactpersoon = contactpersoon;
        this.contactnummer = contactnummer;
    }

    /////////////  TO STRING  /////////////
    @Override
    public String toString() {
        // To Be Continued
        return null;
    }



    ////////  Getters & Setters //////////


    public Integer getId() {
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

    public Persoon getContactpersoon() {
        return contactpersoon;
    }

    public void setContactpersoon(Persoon contactpersoon) {
        this.contactpersoon = contactpersoon;
    }

    public String getContactnummer() {
        return contactnummer;
    }

    public void setContactnummer(String contactnummer) {
        this.contactnummer = contactnummer;
    }
}


