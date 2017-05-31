package be.ift.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Entity
@Table(name = "tblstage")
public class Stage {

    ///////////// Fields   ////////////////
    @Id
    @Column(name = "Stage_ID")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Stage_ID_stagiair")
    @JsonBackReference
    private Stagiair stagiair;

    @ManyToOne
    @JoinColumn(name = "Stage_ID_opdracht")
    @JsonBackReference
    private Stageopdracht stageopdracht;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "Stage_start_Datum")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDatum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Stage_eind_Datum")
    private Date eindDatum;

    @OneToMany
    @JoinColumn(name = "BEGL_ID_stage")
    @JsonManagedReference
    private List<Begeleiding> begeleiding;

    /////////////  Constructor   ////////////////
    public Stage() {
        // Lege constructor
    }

    public Stage(Stagiair stagiair, Stageopdracht stageopdracht, Date startDatum, Date eindDatum){
        this.stagiair = stagiair;
        this.stageopdracht = stageopdracht;
        this.startDatum = startDatum;
        this.eindDatum = eindDatum;
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

    public String getfgStartDatum() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        return sdf.format(startDatum);
    }

    public Date getStartDatum() {
        return startDatum;
    }

    public Date getEindDatum() {
        return eindDatum;
    }

    public void setStartDatum(Date startDatum) {
        this.startDatum = startDatum;
    }

    public void setEindDatum(Date eindDatum) {
        this.eindDatum = eindDatum;
    }

    public String getEinrdDatum() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        return sdf.format(eindDatum);
    }



    public List<Begeleiding> getBegeleiding() {
        return begeleiding;
    }

    public void setBegeleiding(List<Begeleiding> begeleiding) {
        this.begeleiding = begeleiding;
    }
}
