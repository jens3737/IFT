package be.ift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Created by JAHBD44 on 9/05/2017.
 */
@Service
public class MailService
{
    private JavaMailSender mailSender;

    public void sendEmailNewAccount(String email, String naam) {
        SimpleMailMessage message = new SimpleMailMessage();
        String EmailBody = "Beste " + naam + "\n\n" + "Er is voor u een account aangemaakt op de IFT tool van Realdolmen." + "\n\n" + "U kan inloggen op uw account met de volgende gegevens: " + "\n \n" + "Username:   \t\t " + email + "\n" + "Wachtwoord: \t\t changeme \n Gelieve je wachtwoord aan te passen bij de eerste login \n\n\n Met vriendelijke groeten \n IFT Team";
        message.setFrom("ift.noreply@gmail.com");
        message.setTo(email);
        message.setSubject("IFT account aangemaakt");
        message.setText(EmailBody);
        mailSender.send(message);
    }


    public void sendEmailChangedPW(String email, String naam) {
        SimpleMailMessage message = new SimpleMailMessage();
        String Emailbody = "Beste " + naam + "\n\n Uw wachtwoord op de IFT applicatie van Realdolmen is aangepast. Indien deze aanpassing niet door u gemaakt is, gelieve contact op te nemen met de administrator. \n\n\n Met vriendelijke groeten \n IFT Team";
        message.setFrom("ift.noreply@gmail.com");
        message.setTo(email);
        message.setSubject("Wachtwoord gewijzigd");
        message.setText(Emailbody);
        mailSender.send(message);
    }




    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
