import javax.persistence.*;

@Entity
@Table(name = "tblBegeleiding")
public class Begeleiding {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "BEGL_ID_begeleidier")
    private Begeleider begeleider;

    @ManyToOne
    @JoinColumn(name = "BEGL_ID_stage")
    private Stage stage;

    /////////////  Constructor   ////////////////
    public Begeleiding(){

    }

    ////////  Getters & Setters //////////
    public int getId() {
        return id;
    }

    public Begeleider getBegeleider() {
        return begeleider;
    }

    public void setBegeleider(Begeleider begeleider) {
        this.begeleider = begeleider;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
