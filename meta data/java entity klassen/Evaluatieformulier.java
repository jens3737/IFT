import javax.persistence.*;
import java.security.Timestamp;
import java.util.List;

@Entity
@Table(name="tblEvaluatieformulier")
public class Evaluatieformulier {

    ///////////// Fields  ////////////////
    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "EVAL_ID_template")
    private Evaluatietemplate template;

    @ManyToOne
    @JoinColumn(name = "EVAL_ID_stagiair")
    private Stagiair stagiair;

    @Column(name = "EVAL_datum")
    private Timestamp datum;

    @OneToMany
    @JoinColumn(name= "ANTW_ID_evaluatieformulier")
    private List<Antwoord> antwoord;

    /////////////  Constructor   ////////////////
    public Evaluatieformulier() {
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

    public Evaluatietemplate getTemplate() {
        return template;
    }

    public void setTemplate(Evaluatietemplate template) {
        this.template = template;
    }

    public Stagiair getStagiair() {
        return stagiair;
    }

    public void setStagiair(Stagiair stagiair) {
        this.stagiair = stagiair;
    }

    public Timestamp getDatum() {
        return datum;
    }

    public void setDatum(Timestamp datum) {
        this.datum = datum;
    }

    public List<Antwoord> getAntwoord() {
        return antwoord;
    }

    public void setAntwoord(List<Antwoord> antwoord) {
        this.antwoord = antwoord;
    }
}