package be.ift.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@Entity
@Table(name="tblevaluatieformulier")
public class Evaluatieformulier {

    ///////////// Fields  ////////////////
    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "EVAL_ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "EVAL_ID_template")
    @JsonBackReference
    private Evaluatietemplate template;

    @ManyToOne
    @JoinColumn(name = "EVAL_ID_stagiair")
    @JsonBackReference
    private Stagiair stagiair;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Column(name = "EVAL_datum")
    private Date datum;

    @Column(name = "EVAL_gemiddelde_score")
    private double gemiddeldeScore;

    //may have bricked something, need Unit tests please (valon)
    @OneToMany
    @JoinColumn(name= "ANTW_ID_evalformulier")
    @JsonBackReference
//    @JsonManagedReference
    private List<Antwoord> antwoord;


    @ManyToOne
    @JoinColumn(name = "EVAL_ID_begeleider")
    @JsonManagedReference
    private Begeleider begeleider;

    public Begeleider getBegeleider() {
        return begeleider;
    }

    public void setBegeleider(Begeleider begeleider) {
        this.begeleider = begeleider;
    }



    /////////////  Constructor   ////////////////
    public Evaluatieformulier(Date datum, Stagiair stagiair, Begeleider begeleider) {
    this.datum = datum;
    this.stagiair = stagiair;
    this.begeleider = begeleider;
    this.template = null;
    }
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
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public List<Antwoord> getAntwoord() {
        return antwoord;
    }

    public void setAntwoord(List<Antwoord> antwoord) {
        this.antwoord = antwoord;
    }

    public double getGemiddeldeScore() {
        return gemiddeldeScore;
    }

    public void setGemiddeldeScore(double gemiddeldeScore) {
        this.gemiddeldeScore = gemiddeldeScore;
    }
}