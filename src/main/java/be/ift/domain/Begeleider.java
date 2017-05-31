package be.ift.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="tblbegeleider")
public class Begeleider {

    ///////////// Fields   ////////////////
    @Id
    @Column(name = "BEG_ID")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "Beg_ID_persoon")
    private Persoon persoon;

    @Column(name = "Beg_functie_omschrijving")
    private String functieOmschrijving;

    @OneToMany
    @JoinColumn(name="BEGL_ID_begeleider")
    @JsonManagedReference
    private List<Begeleiding> begeleiding;

    @ManyToOne
    @JoinColumn(name="BEG_ID_categorie")
    @JsonBackReference
    private Categorie categorie;

    /////////////  Constructor   ////////////////
    public Begeleider() {
       super();
    }

    public Begeleider(Persoon persoon, String functieOmschrijving, Categorie categorie) {
        this.persoon = persoon;
        this.functieOmschrijving = functieOmschrijving;
        this.categorie = categorie;
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

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(Persoon persoon) {
        this.persoon = persoon;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
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
