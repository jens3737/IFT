import javax.persistence.*;

@Entity
@Table(name = "tblVraag")
public class Vraag {

    /////////////  Fields   ////////////////
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "Vra_ID_template")
    private Evaluatietemplate evaluatieTemplate;

    @Column(name = "Vra_vraagDefinitie")
    private String vraagdefinitie;

    @Column(name = "Vra_type")
    private byte type;



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

    public int getId() {
        return id;
    }

    public Evaluatietemplate getEvaluatieTemplate() {
        return evaluatieTemplate;
    }

    public void setEvaluatieTemplate(Evaluatietemplate evaluatieTemplate) {
        this.evaluatieTemplate = evaluatieTemplate;
    }

    public String getVraagdefinitie() {
        return vraagdefinitie;
    }

    public void setVraagdefinitie(String vraagdefinitie) {
        this.vraagdefinitie = vraagdefinitie;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
