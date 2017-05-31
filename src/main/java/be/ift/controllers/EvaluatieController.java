package be.ift.controllers;

import be.ift.domain.*;
import be.ift.repositories.AntwoordRepository;
import be.ift.services.BegeleiderService;
import be.ift.wrapperClasses.*;
import be.ift.repositories.EvaluatieRepository;
import be.ift.services.servicesimpl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.round;

/**
 * Created by JAHBD44 on 29/03/2017.
 */
@Controller
public class EvaluatieController {

    private EvaluatieRepository evaluatieRepository;
    private BegeleiderServiceImpl begeleiderServiceImpl;
    private VraagServiceImpl vraagServiceImpl;
    private StagiairServiceImpl stagiairServiceImpl;
    private SchaalServiceImpl schaalServiceImpl;
    private UserAccountServiceImpl userAccountServiceImpl;
    private EvaluatietemplateServiceImpl evaluatietemplateServiceImpl;
    private EvaluatieServiceImpl evaluatieServiceImpl;
    private AntwoordServiceImpl antwoordServiceImpl;


    @Autowired
    public void setEvaluatieRepository(EvaluatieRepository evaluatieRepository) {
        this.evaluatieRepository = evaluatieRepository;
    }

    @Autowired
    public void setBegeleiderServiceImpl(BegeleiderServiceImpl begeleiderServiceImpl) {
        this.begeleiderServiceImpl = begeleiderServiceImpl;
    }

    @Autowired
    public void setAntwoordServiceImpl(AntwoordServiceImpl antwoordServiceImpl) {
        this.antwoordServiceImpl = antwoordServiceImpl;
    }

    @Autowired
    public void setVraagServiceImpl(VraagServiceImpl vraagServiceImpl) {
        this.vraagServiceImpl = vraagServiceImpl;
    }

    @Autowired
    public void setStagiairServiceImpl(StagiairServiceImpl stagiairServiceImpl) {
        this.stagiairServiceImpl = stagiairServiceImpl;
    }

    @Autowired
    public void setSchaalServiceImpl(SchaalServiceImpl schaalServiceImpl) {
        this.schaalServiceImpl = schaalServiceImpl;
    }

    @Autowired
    public void setUserAccountServiceImpl(UserAccountServiceImpl userAccountServiceImpl) {
        this.userAccountServiceImpl = userAccountServiceImpl;
    }

    @Autowired
    public void setEvaluatietemplateServiceImpl(EvaluatietemplateServiceImpl evaluatietemplateServiceImpl) {
        this.evaluatietemplateServiceImpl = evaluatietemplateServiceImpl;
    }

    @Autowired
    public void setEvaluatieServiceImpl(EvaluatieServiceImpl evaluatieServiceImpl) {
        this.evaluatieServiceImpl = evaluatieServiceImpl;
    }


    // CREATE evaluatie voor stagiair
    // ID = de id van de stagiair
    @RequestMapping(value = "/evaluatie/{id}", method = RequestMethod.GET)
    public String newEvaluatieStagiair(@PathVariable int id, Model model,@RequestParam(name = "p", defaultValue = "1") int pageNumber) {

        /*Check indien gevraagd stagiair ID wel bestaat, en check of de stagiair wel begeleidt wordt door de ingelogde begeleider*/
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Begeleider begeleider = begeleiderServiceImpl.findBegeleiderByEmail(email);

        List<Stagiair> stagiaires = stagiairServiceImpl.getStagiairesByBegeleider(begeleider.getId());

        if(stagiairServiceImpl.getOneStagiair(id) != null && stagiaires.contains(stagiairServiceImpl.getOneStagiair(id))){
            Evaluatieformulier evaluatieformulier = new Evaluatieformulier();
            double aantalPaginas = vraagServiceImpl.getAantalPaginas();
            List <Vraag> vragenlijst = vraagServiceImpl.getAllVragen();
            for (Vraag vraag : vragenlijst) {
//            System.out.println("Vraag type " + vraag.getId() + " : " + vraag.isType() );
            }

            // Op te vullen
            model.addAttribute("evaluatieformulier", evaluatieformulier);

            // Benodigde data voor evaluatie
            model.addAttribute("aantalPaginas", aantalPaginas);
            model.addAttribute("huidigePagina", pageNumber);
            model.addAttribute("stagiairID", id);
            model.addAttribute("stagiair", stagiairServiceImpl.getOneStagiair(id));
            model.addAttribute("vragenlijst", vraagServiceImpl.getAllVragen(pageNumber));
            model.addAttribute("schalen", schaalServiceImpl.getAllSchalen() );

            return "evaluatiecreate";
        }
        else{
            return "redirect:/404";
        }

    }

    @RequestMapping(value = "/evaluatieformulieropslaan", method = RequestMethod.POST)
    public @ResponseBody Evaluatieformulier saveEvaluatie(@RequestBody List<VraagAntwoordItem> VraagAntwoordItems){
        System.out.println("in ajax controller");
        /*gemiddelde*/
        double aantalAntwoorden = 0;
        double totaalSchaal = 0;
        /*META*/
        int i = 0;
        /*STAGIAIR*/
        int stagiairID;
        Stagiair stagiair;
        /*DATUM*/
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        /*BEGELEIDER*/
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Begeleider currentBegeleider = userAccountServiceImpl.getBegeleiderByEmail(email);
        /*Evaluatieformulier*/
        Evaluatieformulier evaluatieformulier = new Evaluatieformulier();

        for(VraagAntwoordItem vraagAntwoordItem: VraagAntwoordItems){
            if(i==0){
                System.out.println("in for loop");
                stagiairID = vraagAntwoordItem.stagiairID;
                stagiair = stagiairServiceImpl.getOneStagiair(stagiairID);
                evaluatieformulier = new Evaluatieformulier(date, stagiair, currentBegeleider);
                evaluatieServiceImpl.saveEvaluatieformulier(evaluatieformulier);
                i++;
            }

            if(vraagAntwoordItem.getType()){
                //indien openvraag
                Vraag vraag = vraagServiceImpl.getVraagByID(vraagAntwoordItem.getVraagID());
                Antwoord antwoord = new Antwoord(evaluatieformulier, vraag, vraagAntwoordItem.getAntwoord());
                antwoordServiceImpl.saveAntwoord(antwoord);
            }
            else {
                //indien multiple choice
                Schaal schaal = schaalServiceImpl.getSchaalByID(Integer.parseInt(vraagAntwoordItem.getAntwoord()));

                /*per multiple choice, aantalAntwoorden + 1, om gemiddelde te bereken.*/
                /*totaal schaal telt alle schaalwaarden op dmv schaal.getID (want in .getWaarde zit string)*/
                /*Lekker uitbreidbaar!*/
                /*Indietn NVT, niet bij tellen */
                if(schaal.getId() > 0){
                    aantalAntwoorden++;
                    totaalSchaal += schaal.getId();
                }

                Vraag vraag = vraagServiceImpl.getVraagByID(vraagAntwoordItem.getVraagID());
                Antwoord antwoord = new Antwoord(evaluatieformulier, vraag, schaal);
                antwoordServiceImpl.saveAntwoord(antwoord);

            }
        }

        /*Gemiddelde score berekenen dmv MC-vragen*/

        double gemiddeldeScore = totaalSchaal/ aantalAntwoorden;
        System.out.println("gemiddeldeScore: " + gemiddeldeScore);
        gemiddeldeScore = round(gemiddeldeScore, 2);
        System.out.println("gemiddeldeScore  round: " + gemiddeldeScore);
        evaluatieformulier.setGemiddeldeScore(gemiddeldeScore);
        evaluatieServiceImpl.saveEvaluatieformulier(evaluatieformulier);



        return evaluatieformulier;
    }

    public class VragenPageObject {
        public List<Vraag> vragen;
        public List<Schaal> schalen;

        public VragenPageObject(List<Vraag> vragen, List<Schaal> schalen){
            this.vragen = vragen;
            this.schalen = schalen;
        }

    }

    // SHOW evaluatieformulier
    // ID = de id van het evaluatieformulier
    @RequestMapping(value = "/evaluatieformulier/{id}", method = RequestMethod.GET)
    public String showEvaluatie(@PathVariable int id, Model model) {
        // Evaluatietemplate zetten we momenteel hardgecodeerd op het standaard formulier
        int templateID = 1;
        Evaluatietemplate evaluatietemplate = new Evaluatietemplate();
        evaluatietemplate = evaluatietemplateServiceImpl.getOneEvaluatieTemplate(templateID );

        Evaluatieformulier evaluatieformulier = new Evaluatieformulier();
        evaluatieformulier = evaluatieRepository.findEvaluatieByID(id);

        Stagiair stagiair = new Stagiair();


        if(evaluatieformulier.getStagiair() != null){
            stagiair = evaluatieformulier.getStagiair();
            model.addAttribute("begeleider", evaluatieformulier.getBegeleider());
            model.addAttribute("evaluatieformulier", evaluatieformulier );
            model.addAttribute("template", evaluatietemplate );
            model.addAttribute("stagiair", stagiair );
            model.addAttribute("vragen", evaluatietemplate.getVragen());
            model.addAttribute("antwoorden", evaluatieformulier.getAntwoord());

            return "evaluatieshow";
        }
        else{
            return "redirect:/404";
        }

    }


    @RequestMapping(value = "/vragenpage", method = RequestMethod.GET)
    @ResponseBody
    public VragenPageObject ajaxTest(@RequestParam(name = "p", defaultValue = "1") int pageNumber) {
        List<Vraag> vragen = vraagServiceImpl.getAllVragen(pageNumber);
        List<Schaal> schalen = schaalServiceImpl.getAllSchalen();

        VragenPageObject vragenPageObject = new VragenPageObject(vragen, schalen);

        return vragenPageObject;

    }

}
