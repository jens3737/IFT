import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tblPermission")
public class Permission {

    ///////////// Fields   ////////////////
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name = "Perm_name")
    private String naam;

    @Column(name = "Perm_type")
    private String type;

    @OneToMany
    @JoinColumn(name = "RPE_ID_permission")
    private List<RolPermission> rolPermissions;


    /////////////  Constructor   ////////////////
    public Permission() {
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

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<RolPermission> getRolPermissions() {
        return rolPermissions;
    }

    public void setRolPermissions(List<RolPermission> rolPermissions) {
        this.rolPermissions = rolPermissions;
    }

}
