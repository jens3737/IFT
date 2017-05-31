import javax.persistence.*;
import java.security.Timestamp;


@Entity
@Table(name = "tblUserAccount")
public class UserAccount {

    ///////////// Fields   ////////////////
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @OneToOne
    @JoinColumn(name = "Acc_ID_persoon")
    private Persoon persoon;

    @ManyToOne
    @JoinColumn(name = "Acc_ID_rol")
    private Rol rol;

    @Column(name = "Acc_creationDate")
    private Timestamp creationDate;

    @Column(name = "Acc_updatedDate")
    private Timestamp updatedDate;

    @Column(name = "Acc_email")
    private String email;

    @Column(name = "Acc_wachtwoord")
    private String wachtwoord;

    @Column (name = "Per_secretQuestion")
    private String secretQuestion;

    @Column (name = "Acc_secretAnswer")
    private String secretAnswer;

    @Column (name = "Acc_active")
    private byte active;


    /////////////  Constructor   ////////////////
    public UserAccount() {
        // Lege constructor
    }

    /////////////  TO STRING  /////////////
    @Override
    public String toString() {
        // To Be Continued
        return null;
    }

    ////////  Getters & Setters //////////

}
