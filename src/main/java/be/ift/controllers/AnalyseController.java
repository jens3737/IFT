package be.ift.controllers;

import be.ift.domain.Evaluatieformulier;
import be.ift.domain.Stagiair;
import be.ift.services.servicesimpl.*;
import be.ift.wrapperClasses.AnalScorePerSchool;
import org.springframework.stereotype.Controller;
import be.ift.domain.School;
import be.ift.services.SchoolService;
import be.ift.wrapperClasses.AnalStagiairPerSchool;
import be.ift.domain.Categorie;
import be.ift.services.PersoonService;
import be.ift.services.StagiairService;
import be.ift.wrapperClasses.AnalStagiairPerCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AnalyseController {

    private SchoolServiceImpl schoolServiceImpl;
    private AnalyserServiceImpl analyserServiceImpl;
    private StagiairServiceImpl stagiairServicImpl;
    private StageServiceImpl stageServiceImpl;
    private StagiairServiceImpl stagiairServiceImpl;
    private CategorieServiceImpl categorieServiceImpl;

    /*------------------------------WIRING ------------------------- */
    @Autowired
    public void setSchoolServiceImpl(SchoolServiceImpl schoolServiceImpl){this.schoolServiceImpl = schoolServiceImpl;}
    @Autowired
    public void setAnalyserServiceImpl(AnalyserServiceImpl analyserServiceImpl){this.analyserServiceImpl = analyserServiceImpl;}
    @Autowired
    public void setPersoonService(StagiairServiceImpl stagiairServicImpl){this.stagiairServicImpl = stagiairServicImpl;}
    @Autowired
    public void setSchoolService(StageServiceImpl stageServiceImpl){this.stageServiceImpl = stageServiceImpl;}

    @Autowired
    public void setCategorieServiceImpl(CategorieServiceImpl categorieServiceImpl){this.categorieServiceImpl = categorieServiceImpl;}

    @Autowired
    public void setStagiairServiceImpl(StagiairServiceImpl stagiairServiceImpl){
        this.stagiairServiceImpl = stagiairServiceImpl;}

    @RequestMapping("/analyse")
    public String analyseStagiairPerCat(Model model) {
        model.addAttribute("categorieen", categorieServiceImpl.getAllCategories());
        return "analyseshow";
    }

    @RequestMapping("/analyseStagiairPerCategorie")
    @ResponseBody
    public List<AnalStagiairPerCat> analyseStagiairPerCatJson(Model model){
        List<Categorie> categorieen = categorieServiceImpl.getAllCategories();
        List<AnalStagiairPerCat> analStagiairPerCatList = new ArrayList<>();

        for(Categorie categorie: categorieen){
            String categorienaam = categorie.getNaam();
            int aantalStagiaires = stagiairServiceImpl.getAantalStagiairPerCat(categorie.getId());

            if(aantalStagiaires > 0){
                AnalStagiairPerCat analStagiairPerCat = new AnalStagiairPerCat(categorienaam,aantalStagiaires);
                analStagiairPerCatList.add(analStagiairPerCat);
            }

        }
        System.out.println("hihi");
        return analStagiairPerCatList;
    }


    /*JensiePensie Controllerwensie*/
    @RequestMapping("/analyseStagiairPerSchool")
    @ResponseBody
    public List<AnalStagiairPerSchool> analyseStagiairPerSchool(Model model) {
        // Momenteel laadt data voor bar chart aantal stagiaires per school
        // standaard stagejaar = alle jaren tegelijk
        List<School> scholen = schoolServiceImpl.getAllScholen();
        List<AnalStagiairPerSchool> analStagiairPerSchoolList= new ArrayList<>();
        int maxStagiaires = 0;
        for (School school: scholen){
            String schoolNaam = school.getNaam();
            int aantalStagiaires = stagiairServicImpl.getAantalStagiairesBySchool(school.getId());

            /*Jens - houd max stagiaires bij om te weten hoe hoog we de bar chart moeten tekenen*/

            if(aantalStagiaires > 0){
                if(aantalStagiaires > maxStagiaires){
                    maxStagiaires = aantalStagiaires;
                }
                AnalStagiairPerSchool analStagiairPerSchool = new AnalStagiairPerSchool(schoolNaam, aantalStagiaires, maxStagiaires);
                analStagiairPerSchoolList.add(analStagiairPerSchool);

                System.out.println("SchoolNaam: " + analStagiairPerSchool.getSchoolNaam() + " aantal stagiaires: " + analStagiairPerSchool.getAantalStagiaires());
            }
            else{
                System.out.println("Helaas, SchoolNaam: " + schoolNaam + " heeft geen stagiaires");
            }
        }

        model.addAttribute("stagiairesPerSchool", analStagiairPerSchoolList);
        // maak analyse wrapper object

        return analStagiairPerSchoolList;
    }

    @RequestMapping("/analyseGemiddeldeScorePerSchool")
    @ResponseBody
    public List<AnalScorePerSchool> analyseGemiddeldeScorePerSchool(Model model, @RequestParam(name ="c", defaultValue = "1") int catID) {
        /*te returnen lijst*/
        List<AnalScorePerSchool> analScorePerSchoolList = new ArrayList<>();

        /*Alle scholen ophalen*/
        List<School> schoolList = schoolServiceImpl.getAllScholen();

        /*Voor elke school: lijst met stagiaires volgens de opgegeven stage-categorie*/
        for(School school : schoolList) {
            List<Stagiair> stagiairesLijst = analyserServiceImpl.getAllStagiairesPerSchool(school.getId(), catID);
            double totaalEvaluatieformulieren = 0;
            int totaalStagiaires = stagiairesLijst.size();
            double totaalScore = 0;
            double gemiddeldeScore = 0;
            String schoolNaam = school.getNaam();

            /*binnen elke school, per stagiair gemiddelde van alle evaluatieformulieren maken*/
            for(Stagiair stagiair: stagiairesLijst){
                /*Work in progress*/
                List<Evaluatieformulier> evaluatieformulieren = stagiair.getEvaluatieformulier();

                /*Voor alle evaluatieformulieren, gemiddelde ophalen --> algemeen gemiddelde van alle formulieren maken*/
                for(Evaluatieformulier evaluatieformulier : evaluatieformulieren){
                    totaalScore += evaluatieformulier.getGemiddeldeScore();
                    totaalEvaluatieformulieren ++;
                }
            }

            /*enkel toevoegen als er een gemiddelde kon berekend worden*/
            if(totaalEvaluatieformulieren > 0){
                gemiddeldeScore = (totaalScore / totaalEvaluatieformulieren);
                AnalScorePerSchool analScorePerSchool = new AnalScorePerSchool(schoolNaam, totaalStagiaires, gemiddeldeScore);
                analScorePerSchoolList.add(analScorePerSchool);
            }

        }

        return analScorePerSchoolList;
    }

}


