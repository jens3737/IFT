package be.ift.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "tblvraag")
public class Vraag {

    /////////////  Fields   ////////////////
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "VRA_ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Vra_ID_template")
    @JsonBackReference
    private Evaluatietemplate evaluatieTemplate;

    @Column(name = "Vra_definitie")
    private String vraagdefinitie;



    @Column(name = "Vra_type")
    private boolean type;

    /////////////  Constructor   ////////////////
    public Vraag() {
        // Lege constructor
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

    public Evaluatietemplate getEvaluatieTemplate() {
        return evaluatieTemplate;
    }

    public void setEvaluatieTemplate(Evaluatietemplate evaluatieTemplate) {
        this.evaluatieTemplate = evaluatieTemplate;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getVraagdefinitie() {
        return vraagdefinitie;
    }

    public void setVraagdefinitie(String vraagdefinitie) {
        this.vraagdefinitie = vraagdefinitie;
    }



}
