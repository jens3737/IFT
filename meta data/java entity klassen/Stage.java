import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tblStage")
public class Stage {

    ///////////// Fields   ////////////////
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "Stage_ID_stagair")
    private Stagiair stagiair;

    @ManyToOne
    @JoinColumn(name = "Stage_ID_opdracht")
    private Stageopdracht stageopdracht;

    @Column(name = "Stage_startDatum")
    private Date startDatum;

    @Column(name = "Stage_eindDatum")
    private Date eindDatum;

    @OneToMany
    @JoinColumn(name = "BEG_ID_stage")
    private List<Begeleiding> begeleiding;

    /////////////  Constructor   ////////////////
    public Stage() {
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

    public void setId(int id) {
        this.id = id;
    }

    public Stagiair getStagiair() {
        return stagiair;
    }

    public void setStagiair(Stagiair stagiair) {
        this.stagiair = stagiair;
    }

    public Stageopdracht getStageopdracht() {
        return stageopdracht;
    }

    public void setStageopdracht(Stageopdracht stageopdracht) {
        this.stageopdracht = stageopdracht;
    }

    public Date getStartDatum() {
        return startDatum;
    }

    public void setStartDatum(Date startDatum) {
        this.startDatum = startDatum;
    }

    public Date getEindDatum() {
        return eindDatum;
    }

    public void setEindDatum(Date eindDatum) {
        this.eindDatum = eindDatum;
    }

    public List<Begeleiding> getBegeleiding() {
        return begeleiding;
    }

    public void setBegeleiding(List<Begeleiding> begeleiding) {
        this.begeleiding = begeleiding;
    }
}
