package be.ift.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "tblbegeleiding")
public class Begeleiding {

    @Id
    @Column(name = "BEGL_ID")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "BEGL_ID_begeleider")
    @JsonBackReference
    private Begeleider begeleider;

    @ManyToOne
    @JoinColumn(name = "BEGL_ID_stage")
    @JsonBackReference
    private Stage stage;

    /////////////  Constructor   ////////////////
    public Begeleiding(){

    }

    public Begeleiding(Begeleider begeleider, Stage stage) {
        this.begeleider = begeleider;
        this.stage = stage;
    }

    ////////  Getters & Setters //////////


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
