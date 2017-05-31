package be.ift.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tblevaluatietemplate")
public class Evaluatietemplate {

    /////////////  Fields   ////////////////
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "TEMP_ID")
    private Integer id;

    @Column(name = "TEMP_naam")
    private String naam;

    @OneToMany
    @JoinColumn(name = "VRA_ID_template")
//    @JsonManagedReference
    @JsonBackReference
    private List<Vraag> vragen;
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

    public List<Vraag> getVragen() {
        return vragen;
    }

    public void setVragen(List<Vraag> vragen) {
        this.vragen = vragen;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
}
