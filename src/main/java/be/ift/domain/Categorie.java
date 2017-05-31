package be.ift.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tblcategorie")
public class Categorie {

    /////////////  Fields   ////////////////
    @Id
    @Column(name="CAT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "CAT_naam")
    private String naam;

    @OneToMany
    @JoinColumn(name="BEG_ID_categorie")
    @JsonBackReference
    private List<Begeleider> begeleiders;

    /////////////  Constructors  /////////////
    public Categorie(){

    }

    public Categorie(String naam) {
        this.naam = naam;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Begeleider> getBegeleiders() {
        return begeleiders;
    }

    public void setBegeleiders(List<Begeleider> begeleiders) {
        this.begeleiders = begeleiders;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
}
