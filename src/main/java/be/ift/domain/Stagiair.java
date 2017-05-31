package be.ift.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "tblstagiair")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Stagiair {
    ///////////// Fields   ////////////////
    @Id
    @Column(name = "STA_ID")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name= "STA_ID_school")
//    @JsonBackReference
    @JsonManagedReference
    private School school;

    @OneToOne
    @JoinColumn(name = "STA_ID_persoon")
    private Persoon persoon;

    @Column(name = "STA_studierichting")
    @Size(min = 1, max = 25)
    private String studierichting;

    @OneToMany
    @JoinColumn(name = "STAGE_ID_stagiair")
    @JsonManagedReference
    private List<Stage> stage;

    @OneToMany
    @JoinColumn(name = "Eval_ID_stagiair")
    @JsonManagedReference
    private List<Evaluatieformulier> evaluatieformulier;

    /////////////  TO STRING  /////////////
    @Override
    public String toString() {
        // To Be Continued
        return null;
    }

    /////////////  Constructor   ////////////////
    public Stagiair() {
        super();
    }

    public Stagiair(School school, Persoon persoon, String studierichting) {
        this.school = school;
        this.persoon = persoon;
        this.studierichting = studierichting;
    }
    ////////  Getters & Setters //////////

    public List<Evaluatieformulier> getEvaluatieformulier() {
        return evaluatieformulier;
    }

    public void setEvaluatieformulier(List<Evaluatieformulier> evaluatieformulier) {
        this.evaluatieformulier = evaluatieformulier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(Persoon persoon) {
        this.persoon = persoon;
    }

    public String getStudierichting() {
        return studierichting;
    }

    public void setStudierichting(String studierichting) {
        this.studierichting = studierichting;
    }

    public List<Stage> getStage() {
        return stage;
    }

    public void setStage(List<Stage> stage) {
        this.stage = stage;
    }
}
