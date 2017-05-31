import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tblStagair")
public class Stagiair extends Persoon{
    ///////////// Fields   ////////////////
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name= "STA_ID_school")
    private School school;

    @OneToOne
    @JoinColumn(name = "STA_ID_persoon")
    private Persoon persoon;

    @Column(name = "STA_campus")
    private String campus;

    @Column(name = "STA_studieRichting")
    private String studierichting;

    @OneToMany
    @JoinColumn(name = "STAGE_ID_stagiair")
    private List<Stage> stage;

    @OneToMany
    @JoinColumn(name = "EVAL_ID_stagiair")
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


    ////////  Getters & Setters //////////

    public int getId() {
        return id;
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

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
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

    public List<Evaluatieformulier> getEvaluatieformulier() {
        return evaluatieformulier;
    }

    public void setEvaluatieformulier(List<Evaluatieformulier> evaluatieformulier) {
        this.evaluatieformulier = evaluatieformulier;
    }

}
