/**
 * Created by JHNBD42 on 27/02/2017.
 */
import javax.persistence.*;

@Entity
@Table(name="tblAntwoord")
public class Antwoord {

    ///////////// Fields   ////////////////

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name = "Antw_openvraag")
    private String openvraag;

    @ManyToOne
    @JoinColumn(name = "Antw_ID_evaluatieformulier")
    private Evaluatieformulier evaluatieformulier;

    @ManyToOne
    @JoinColumn(name = "Antw_ID_schaal")
    private Schaal schaal;

    @ManyToOne
    @JoinColumn(name = "Antw_ID_vraag")
    private Vraag id_vraag;


    ///////////// Constructors   ////////////////
    public Antwoord(){

    }

    ///////////// Gettesr & Setters   ////////////////
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Vraag getId_vraag() {
        return id_vraag;
    }

    public void setId_vraag(Vraag id_vraag) {
        this.id_vraag = id_vraag;
    }
}
