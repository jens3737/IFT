/**
 * Created by JHNBD42 on 27/02/2017.
 */
package be.ift.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name="tblantwoord")
public class Antwoord {

    ///////////// Fields   ////////////////

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ANTW_ID")
    private Integer id;

    @Column(name = "Antw_voluit")
    private String openvraag;

    @ManyToOne
    @JoinColumn(name = "Antw_ID_evalformulier")
    @JsonBackReference
    private Evaluatieformulier evaluatieformulier;

    @ManyToOne
    @JoinColumn(name = "Antw_ID_schaal")
    @JsonBackReference
    private Schaal schaal;

    @ManyToOne
    @JoinColumn(name = "Antw_ID_vraag")
    @JsonBackReference
    public Vraag vraag;

    @Override
    public String toString() {
        return "Antwoord{" +
                "id=" + id +
                ", openvraag='" + openvraag + '\'' +
                ", evaluatieformulier=" + evaluatieformulier.getId() +
//                ", schaal=" + schaal.getWaarde() +
                ", vraag=" + vraag.getVraagdefinitie() +
                '}';
    }

    ///////////// Constructors   ////////////////
    public Antwoord(){

    }

    //    antwoord van open vraag
    public Antwoord(Evaluatieformulier evaluatieformulier, Vraag vraag, String openvraag) {
        this.evaluatieformulier = evaluatieformulier;
        this.vraag = vraag;
        this.openvraag = openvraag;

    }
    //    antwoord van multiple choice vraag
    public Antwoord(Evaluatieformulier evaluatieformulier, Vraag vraag, Schaal schaal) {
        this.evaluatieformulier = evaluatieformulier;
        this.vraag = vraag;
        this.schaal = schaal;
    }

    ///////////// Gettesr & Setters   ////////////////
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenvraag() {
        return openvraag;
    }

    public void setOpenvraag(String openvraag) {
        this.openvraag = openvraag;
    }

    public Evaluatieformulier getEvaluatieformulier() {
        return evaluatieformulier;
    }

    public void setEvaluatieformulier(Evaluatieformulier evaluatieformulier) {
        this.evaluatieformulier = evaluatieformulier;
    }

    public Schaal getSchaal() {
        return schaal;
    }

    public void setSchaal(Schaal schaal) {
        this.schaal = schaal;
    }

    public Vraag getVraag() {
        return vraag;
    }



    public void setVraag(Vraag vraag) {
        this.vraag = vraag;
    }
}
