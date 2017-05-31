/**
 * Created by JHNBD42 on 27/02/2017.
 */

import javax.persistence.*;


@Entity
@Table(name="tblSchaal")
public class Schaal {

    //Fields//
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int id;

    @Column (name = "SCHAAL_waarde")
    String waarde;

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
