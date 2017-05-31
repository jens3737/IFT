package be.ift.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Table(name = "tbluseraccount")
public class UserAccount {

    ///////////// Fields   ////////////////
    @Id
    @Column(name = "Acc_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "Acc_ID_persoon")
    private Persoon persoon;

    @ManyToOne
    @JoinColumn(name = "Acc_ID_rol")
    @JsonBackReference
    private Rol rol;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Column(name = "Acc_creation_Date")
    private Date creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Column(name = "Acc_update_Date")
    private Date updatedDate;

    @Column(name = "Acc_email")
    @Email
    private String email;

    @Column(name = "Acc_wachtwoord")
    @Size(min = 3, max = 80)
    private String wachtwoord;

    @Column (name = "Acc_secret_Question")
    private String secretQuestion;

    @Column (name = "Acc_secret_Answer")
    private String secretAnswer;

    @Column (name = "Acc_active")
    private boolean active;


    /////////////  Constructor   ////////////////
    public UserAccount() {
        // Lege constructor
    }

    public UserAccount(Persoon persoon, Rol rol, String email, boolean active, String wachtwoord) {
        this.persoon = persoon;
        this.rol = rol;
        this.email = email;
        this.active = active;
        this.wachtwoord = wachtwoord;
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

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(Persoon persoon) {
        this.persoon = persoon;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
