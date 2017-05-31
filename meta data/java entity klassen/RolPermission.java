import javax.persistence.*;

@Entity
@Table(name = "tblRolPermission")
public class RolPermission {

    ///////////// Fields   ////////////////
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "RPE_ID_permission")
    private Permission permission;

    @ManyToOne
    @JoinColumn(name = "RPE_ID_rol")
    private Rol rol;

    /////////////  Constructor   ////////////////
    public RolPermission() {
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

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
