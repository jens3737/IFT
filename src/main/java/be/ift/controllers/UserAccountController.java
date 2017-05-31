package be.ift.controllers;

import be.ift.MailService;
import be.ift.domain.*;
import be.ift.services.BegeleiderService;
import be.ift.services.PersoonService;
import be.ift.services.UserAccountService;
import be.ift.services.servicesimpl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JAHBD44 on 20/03/2017.
 */

@Controller
public class UserAccountController {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private RolServiceImpl rolServiceImpl;
    private CategorieServiceImpl categorieServiceImpl;
    private UserAccountServiceImpl userAccountServiceImpl;
    private UserAccountService userAccountService;
    private PersoonService persoonService;
    private BegeleiderService begeleiderService;
    private PersoonServiceImpl persoonServiceImpl;
    private HashingServiceImpl hashingServiceImpl;
    private MailService mailService;

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }


    @Autowired
    public void setHashingServiceImpl(HashingServiceImpl hashingServiceImpl) {
        this.hashingServiceImpl = hashingServiceImpl;
    }

    @Autowired
    public void setRolServiceImpl(RolServiceImpl rolServiceImpl) {
        this.rolServiceImpl = rolServiceImpl;
    }

    @Autowired
    public void setCategorieServiceImpl(CategorieServiceImpl categorieServiceImpl) {
        this.categorieServiceImpl = categorieServiceImpl;
    }

    @Autowired
    public void setUserAccountServiceImpl(UserAccountServiceImpl userAccountServiceImpl) {
        this.userAccountServiceImpl = userAccountServiceImpl;
    }

    @Autowired
    public void setPersoonService(PersoonService persoonService){
        this.persoonService = persoonService;
    }

    @Autowired
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Autowired
    public void setBegeleiderService( BegeleiderService begeleiderService) {
        this.begeleiderService = begeleiderService;
    }

    @RequestMapping("/profiel")
    public String getAuthenticatedUser(Model model) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            UserAccount userAccount = userAccountServiceImpl.findOneByEmail(email);
            model.addAttribute("CurrentUser", (UserAccount) userAccount);
            return "redirect:/useraccount/" + userAccount.getId() ;
    }



    @RequestMapping("/useraccount")
    public String newUserAccount(Model model){
        Integer rol_id = 0;
        String functieomschrijving = "";
        String email = "";
        model.addAttribute("persoon", new Persoon() );
        model.addAttribute("rol_id", rol_id );
        model.addAttribute("categorieen",categorieServiceImpl.getAllCategories() );
        model.addAttribute("functieomschrijving", functieomschrijving);
        model.addAttribute("rollen", rolServiceImpl.getAllRollen());
        model.addAttribute("categorieen", categorieServiceImpl.getAllCategories());

        return "useraccountcreate";
    }

    @RequestMapping(value = "/useraccount/{id}", method = RequestMethod.GET)
    public String showuseraccount(@PathVariable int id, Model model)
    {
        //Ingelogde gebruiker en te bekijken gebruiker data klaarzetten
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserAccount userBekijken = userAccountServiceImpl.getOneUserAccount(id);
        UserAccount userIngelogd = userAccountServiceImpl.findOneByEmail(email);
        String rol = userAccountServiceImpl.getRolByEmail(email).getNaam();
        System.out.println("De rol van de ingelogde gebruiker is: " + userAccountServiceImpl.getRolByEmail(email).getBenaming());
        String functieomschrijving = "";

        // Controlleren of je jouw eigen account bekijkt
        if (userBekijken == userIngelogd) {
            System.out.println("de rol binnen de userBekijken == userInelogd: " + rol);
            // Controlleren op de ROL
            if (rol.equals("ROLE_STAGECOORDINATOR") ) {
                System.out.println("gebruiker is stagecoordinator");
                // Heeft deze persoon een functieomschrijving?
                model.addAttribute("isBegeleider", true);
                try {
                    functieomschrijving = userAccountServiceImpl.getBegeleiderByEmail(email).getFunctieOmschrijving();
                    model.addAttribute("functieomschrijving", functieomschrijving );
                    System.out.println("Functieomschrijving coordinator: " + functieomschrijving);
                } catch (NullPointerException e) {
                    System.out.println("Error.tostring " +  e.toString() + " Errormessage: " + e.getMessage() );
                }
            }
            // Controle op Rol
            else if (rol.equals("ROLE_BEGELEIDER")) {
                System.out.println("gebruiker is een begeleider");
                model.addAttribute("isBegeleider", true);
                // Heeft deze persoon een functieomschrijving?
                try {
                    functieomschrijving = userAccountServiceImpl.getBegeleiderByEmail(email).getFunctieOmschrijving();
                    model.addAttribute("functieomschrijving", functieomschrijving );
                    System.out.println("Functieomschrijving begeleider: " + functieomschrijving);
                } catch (NullPointerException e) {
                    System.out.println("Error.tostring " +  e.toString() + " Errormessage: " + e.getMessage() );
                }
            }
            model.addAttribute("useraccount", userBekijken);
            model.addAttribute("rol" , rol);
            return "useraccountshow";
        }
        else {
            // Deze functie zal een gebruiker naar zijn eigen profiel brengen indien er een foutieve ID wordt meegegeven
            return "redirect:/useraccount/" + userIngelogd.getId();
        }

    }


    // Controle
    // Aanmaken nieuw useraaccount
    // Email reeds in DB?
    @RequestMapping(value = "/checkEmailExists")
    @ResponseBody
    public boolean checkEmailExists(@RequestParam("email") String email  ){
        boolean emailExists = false;
        System.out.println(email);
        if (userAccountServiceImpl.findOneByEmail(email) != null ){
            emailExists = true;
        }
        return emailExists;
    }

    @RequestMapping(value = "/useraccountpasswordchange")
    @ResponseBody
    public int changePassword(@RequestParam("huidigWachtwoord") String huidigWachtwoord, @RequestParam("nieuwWachtwoord") String nieuwWachtwoord, @RequestParam("herhaalWachtwoord") String herhaalWachtwoord){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserAccount user = userAccountServiceImpl.findOneByEmail(auth.getName());
        /*Eerst kijken of "huidig wachtwoord" wel klopt*/
        if(hashingServiceImpl.passwordEncoder.matches(huidigWachtwoord, user.getWachtwoord())){
            /*Return 0 als wachtwoord veranderd is*/
            if(!huidigWachtwoord.equals(nieuwWachtwoord)){
                user.setWachtwoord(hashingServiceImpl.passwordEncoder.encode(nieuwWachtwoord));
                userAccountServiceImpl.saveUserAccount(user);
                return 0;
            }

//            return 1 indien nieuwWachtwoord hetzelfde is als huidigWachtwoord
            else{
                return 1;
            }
        }
        /* return 2 indien Huidig wachtwoord fout ingegeven*/
        else{
            return 2;
        }
    }


    //cat Id weg gedaan
    @RequestMapping(value = "/useraccountopslaan", method = RequestMethod.POST)
    public String saveUseraccount(@Valid Persoon persoon,BindingResult result, Integer rol_id, int categorie_id ,String functieomschrijving){

        persoonService.savePersoon(persoon);
        String email = persoon.getEmail();

        if (result.hasErrors()) {

            return "redirect:/403";

        } else {
            if(rol_id == 2 || rol_id == 1){
                Categorie categorie = categorieServiceImpl.getCategorieByID(categorie_id);
                Begeleider begeleider = new Begeleider(persoon, functieomschrijving, categorie);
                begeleiderService.saveBegeleider(begeleider);
                Rol rol = rolServiceImpl.getOneRol(rol_id);
                UserAccount useraccount = new UserAccount(persoon,rol, email, true, hashingServiceImpl.passwordEncoder.encode("changeme") );
                userAccountServiceImpl.saveUserAccount(useraccount);
                mailService.sendEmailNewAccount(email, persoon.getNaam());
            }
            else{
                Rol rol = rolServiceImpl.getOneRol(rol_id);
                UserAccount useraccount = new UserAccount(persoon,rol, email, true, hashingServiceImpl.passwordEncoder.encode("changeme") );
                userAccountServiceImpl.saveUserAccount(useraccount);
            }

        }

        return "redirect:/home";
    }

    @RequestMapping(value = "/useraccountupdate")
    @ResponseBody
    public UserAccount useraccountupdate(@RequestParam("id")Integer id, @RequestParam("naam") String naam, @RequestParam("email") String email, @RequestParam("gsm") String gsm, @RequestParam("geboortedatum") String geboortedatum, @RequestParam("adres") String adres, @RequestParam(name = "functie", defaultValue = "false") String functie){
        UserAccount userAccount = userAccountServiceImpl.getOneUserAccount(id);

        /*format date*/
        String day = geboortedatum.substring(8,10);
        String month = geboortedatum.substring(5,7);
        String year = geboortedatum.substring(0,4);
        String formattedGeboortedatum = day + "/" + month + "/" + year;
        Date date;

        try{
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            date = format.parse(formattedGeboortedatum);
            userAccount.getPersoon().setGeboorteDatum(date);
        }
        catch (java.text.ParseException p){
            p.getStackTrace();
        }

        userAccount.setEmail(email);
        userAccount.getPersoon().setNaam(naam);
        userAccount.getPersoon().setEmail(email);
        userAccount.getPersoon().setAdres(adres);
        userAccount.getPersoon().setGsm(gsm);

        userAccountServiceImpl.saveUserAccount(userAccount);

        //indien wel ook functie updaten
        if(!functie.equals("false")){
            Begeleider begeleider = userAccountServiceImpl.getBegeleiderByEmail(email);
            begeleider.setFunctieOmschrijving(functie);
            begeleiderService.saveBegeleider(begeleider);
        }
        return  userAccount;
    }
}
