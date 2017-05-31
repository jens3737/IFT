package be.ift.domain;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tblrol")
public class Rol {

    ///////////// Fields   ////////////////
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column (name = "Rol_id")
    private Integer id;

    @Column(name = "Rol_naam")
    private String naam;

    @OneToMany
    @JoinColumn(name = "RPE_ID_rol")
    @JsonManagedReference
    private List<RolPermission> rolPermission;

    @Column (name = "Rol_benaming")
    private String benaming;

    /////////////  Constructor   ////////////////
    public Rol() {
        // Lege constructor
    }

    public Rol(String naam, List<RolPermission> rolPermission, String benaming) {
        this.naam = naam;
        this.rolPermission = rolPermission;
        this.benaming = benaming;
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

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public List<RolPermission> getRolPermission() {
        return rolPermission;
    }

    public void setRolPermission(List<RolPermission> rolPermission) {
        this.rolPermission = rolPermission;
    }

    public String getBenaming() {
        return benaming;
    }

    public void setBenaming(String benaming) {
        this.benaming = benaming;
    }
}
