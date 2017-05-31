/**
 * Created by JHNBD42 on 27/02/2017.
 */

package be.ift.domain;
import javax.persistence.*;


@Entity
@Table(name="tblschaal")
public class Schaal {

    //Fields//
    @Id
    @Column(name="SCHAAL_ID")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;


    @Column (name = "SCHAAL_waarde")
    private String waarde;

    //constructor//

    public Schaal()
    {

    }

    //Getters & Setters//


    public int getId() {
        return id;
    }

    public String getWaarde() {
        return waarde;
    }

    public void setWaarde(String waarde) {
        this.waarde = waarde;
    }
}
