package be.ift.controllers;

// Imports
import be.ift.domain.*;
import be.ift.services.*;
import be.ift.services.servicesimpl.*;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.context.SecurityContextHolder;

import be.ift.domain.Stage;
import be.ift.services.StagiairService;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.Console;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Controller
public class StagiairController {

    // Services & implementaties
    private StagiairService stagiairService;
    private StagiairServiceImpl stagiairServiceImpl;
    private SchoolServiceImpl schoolServiceImpl;
    private CategorieServiceImpl categorieServiceImpl;
    private PersoonService persoonService;
    private BegeleidingServiceImpl begeleidingServiceImpl;
    private UserAccountServiceImpl userAccountServiceImpl;
    private EvaluatieServiceImpl evaluatieServiceImpl;

    @Autowired
    public void setStagiairService(StagiairService stagiairService){
        this.stagiairService = stagiairService;
    }
    @Autowired
    public void setEvaluatieServiceImpl(EvaluatieServiceImpl evaluatieServiceImpl){
        this.evaluatieServiceImpl = evaluatieServiceImpl;
    }
    @Autowired
    public void setPersoonService(PersoonService persoonService){
        this.persoonService = persoonService;
    }
    @Autowired
    public void setCategorieServiceImpl(CategorieServiceImpl categorieServiceImpl){this.categorieServiceImpl = categorieServiceImpl;}

    @Autowired
    public void setStagiairServiceImpl(StagiairServiceImpl stagiairServiceImpl){
        this.stagiairServiceImpl = stagiairServiceImpl;
    }

    @Autowired
    public void setSchoolServiceImpl(SchoolServiceImpl schoolServiceImpl){
        this.schoolServiceImpl = schoolServiceImpl;
    }

    @Autowired
    public void setBegeleiderServiceImpl(BegeleidingServiceImpl begeleidingServiceImpl) {
        this.begeleidingServiceImpl = begeleidingServiceImpl;
    }

    @Autowired
    public void setUserAccountServiceImpl(UserAccountServiceImpl userAccountServiceImpl) {
        this.userAccountServiceImpl = userAccountServiceImpl;
    }

    /*------------------------------GET ------------------------- */
    @RequestMapping("/stagiair")
    public String newStagiair(Model model){
        String studierichting = "";
        Integer school_id = 0;
        model.addAttribute("persoon", new Persoon() );
        model.addAttribute("school_id", school_id );
        model.addAttribute("studierichting", studierichting);
        model.addAttribute("scholen", schoolServiceImpl.getAllScholen());
        return "stagiaircreate";
    }

    @RequestMapping(value = "/stagiaires", method = RequestMethod.GET)
    public String viewStagiaires(@RequestParam(name = "p", defaultValue = "1") int pageNumber, Model model) {

        /*standaard*/
        double aantalPaginas = stagiairServiceImpl.getAantalPaginas();

        if(model.containsAttribute("stagiaires")){

            List<Stagiair> stagiaires = (List<Stagiair>) model.asMap().get("stagiaires");

            for(Stagiair stagiair: stagiaires){
                System.out.println("in if: " + stagiair.getPersoon().getNaam());
            }
            System.out.println("haalo er is al een stagiairmodelobject meegegeven");
//            for(Stagiair stagiair: ){
//                System.out.println("in if: " + stagiair.getPersoon().getNaam());
//            }
        }


        if (!model.containsAttribute("stagiaires")) {

            List<Stagiair> stagiaires = stagiairServiceImpl.getAllStagiaires(pageNumber);
            model.addAttribute("stagiaires", stagiaires);

            for(Stagiair stagiair: stagiaires){
                System.out.println("in if: " + stagiair.getPersoon().getNaam());
            }
        }
        
        model.addAttribute("aantalPaginas", aantalPaginas);
        model.addAttribute("huidigePagina", pageNumber);

        /*filter */
        Integer categorie_id = 0;
        Integer school_id = 0;

        model.addAttribute("categorie_id", categorie_id);
        model.addAttribute("school_id", school_id);
        model.addAttribute("categorieen", categorieServiceImpl.getAllCategories());
        model.addAttribute("scholen", schoolServiceImpl.getAllScholen());

        return "stagiaires";
    }

    @RequestMapping(value = "/stagiairespage", method = RequestMethod.GET)
    @ResponseBody
    public FilteredListObject getNextPage(@RequestParam(name = "p", defaultValue = "1") int pageNumber) {
        List<Stagiair> stagiaires = stagiairServiceImpl.getAllStagiaires(pageNumber);
        double aantalPaginas = stagiairServiceImpl.getAantalPaginas();
        FilteredListObject filteredListObject = new FilteredListObject(stagiaires, aantalPaginas);
        return filteredListObject;

    }
    public class FilteredListObject {
        public List<Stagiair> stagiaires;
        public double aantalPaginas;
        public Integer categorieID;
        public Integer schoolID;

        public FilteredListObject(List<Stagiair> stagiaires, double aantalPaginas) {
            this.stagiaires = stagiaires;
            this.aantalPaginas = aantalPaginas;
        }

        public FilteredListObject(List<Stagiair> stagiaires, double aantalPaginas, Integer categorieID, Integer schoolID) {
            this.stagiaires = stagiaires;
            this.aantalPaginas = aantalPaginas;
            this.categorieID = categorieID;
            this.schoolID = schoolID;
        }
    }

    @RequestMapping(value = "/stagiairesFilter", method = RequestMethod.GET)
    @ResponseBody
    public FilteredListObject getFilteredPage(@RequestParam(name = "p", defaultValue = "1") int pageNumber, @RequestParam(name ="fc", defaultValue = "0") Integer catID, @RequestParam(name ="fs", defaultValue = "0") Integer schoolID) {
        System.out.println("halo?");
        int queryOffset = 0;
        if (pageNumber != 1){
            queryOffset = (pageNumber * 10) - 10;
        }

        List<Stagiair> stagiaires;
        double aantalPaginas;

        Categorie categorie = categorieServiceImpl.getCategorieByID(catID);
        School school = schoolServiceImpl.getOneSchool(schoolID);

        if(catID < 1 && schoolID > 0){
            /*Doe sorteer op school*/
            System.out.println("In else/if sorteer school");
            stagiaires = stagiairServiceImpl.getAllStagiairesFSchool(schoolID, queryOffset);
            aantalPaginas = stagiairServiceImpl.getAantalPaginasFSchool(schoolID);
        }

        else if(catID > 0 && schoolID < 1){
            /*Doe sorteer op cat*/
            System.out.println("In else/if sorteer categorie");
            stagiaires = stagiairServiceImpl.getAllStagiairesFCategorie(catID, queryOffset);
            aantalPaginas = stagiairServiceImpl.getAantalPaginasFCategorie(catID);
        }
        else{
            /*doe beide*/
            System.out.println("In else/if sorteer beide");
            stagiaires = stagiairServiceImpl.getAllStagiairesFCategorieSchool(schoolID, catID, queryOffset);
            aantalPaginas = stagiairServiceImpl.getAantalPaginasFSchoolCategorie(schoolID,catID);
        }


        FilteredListObject filteredListObject = new FilteredListObject(stagiaires, aantalPaginas, catID, schoolID);
        return filteredListObject;
    }

    @RequestMapping("/stagiair/{id}")
    public String showStagiair(@PathVariable int id, Model model)
    {
        if(stagiairServiceImpl.getOneStagiair(id) != null){
            //Controlleren of ingelogde gebruiker begeleider is van deze stagiair
            //indien ja, knop om te evalueren tonen
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
//            Rol rol = userAccountServiceImpl.getRolByEmail(email);
//
//            switch (rol.getNaam()){
//                case "ROLE_BEGELEIDER": case "ROLE_STAGECOORDINATOR":
//                    Begeleider begeleider = userAccountServiceImpl.getBegeleiderByEmail(email);
//
//                    if(stagiairServiceImpl.getCurrentStage(id) != null){
//                        Stage stage= stagiairServiceImpl.getCurrentStage(id);
//                        Begeleiding begeleiding = stagiairServiceImpl.getCurrentBegeleidingByStageID(stage.getId());
//                    }
//                    break;
//                case "ROLE_HR":
//                    break;
//            }

            List<Begeleiding> begeleidingList = new ArrayList<Begeleiding>();
//            if(begeleidingServiceImpl.BegeleidingFBegeleider(begeleider.getId()) != null){
//                begeleidingList = begeleidingServiceImpl.BegeleidingFBegeleider(begeleider.getId());
//            }

            // Stagiair ophalen
            Stagiair stagiair = stagiairServiceImpl.getOneStagiair(id);
            DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");

            // Stagiair evaluaties ophalen
            List<Evaluatieformulier> evaluatieformulieren = evaluatieServiceImpl.getEvaluatieformulieren(stagiair.getId());
            //Controlleren of ingelogde gebruiker begeleider is van de te bekijken stagiair
            boolean isBegeleider = begeleidingList.stream()
                    .filter(begeleiding -> begeleiding.getStage() != null)
                    .filter(begeleiding -> begeleiding.getStage().getStagiair() != null)
                    .anyMatch(begeleiding -> begeleiding.getStage().getStagiair().getId().equals(stagiair.getId()));

            // Als begeleider = true => boolean via model.addAttribute meegeven aan Front-End
            if (isBegeleider) {
                model.addAttribute("isBegeleider", isBegeleider);
            }

            // Huidige Stage ophalen
            Stage stage = new Stage();
            if(stagiairServiceImpl.getCurrentStage(id) != null) {

                stage = stagiairServiceImpl.getCurrentStage(id);

                String startDatum = outputFormatter.format(stage.getStartDatum());
                String eindDatum = outputFormatter.format(stage.getEindDatum());
                model.addAttribute("startDatum", startDatum);
                model.addAttribute("eindDatum", eindDatum);
                model.addAttribute("begeleiding", stagiairServiceImpl.getCurrentBegeleidingByStageID(stage.getId()));
            }
            if(evaluatieformulieren.size() > 0){
                model.addAttribute("evaluatieformulieren", evaluatieformulieren);
            }
            model.addAttribute("stage", stage);


            List<Stage> afgelopenStages = stagiairServiceImpl.getOldStages(id);
            model.addAttribute("afgelopenStages", afgelopenStages);

            // Geboortedatum ophalen
            String geboorteDatum = outputFormatter.format(stagiair.getPersoon().getGeboorteDatum());
            model.addAttribute("stagiair", stagiair);
            model.addAttribute("geboorteDatum", geboorteDatum);

            return "stagiairshow";
        }
        else{
            return "redirect:/404";
        }


    }

    //--------------------POST------------------------

    @RequestMapping(value = "/stagiairopslaan", method = RequestMethod.POST)
    public String savePersoon(Persoon persoon, Integer school_id, String studierichting){
        persoonService.savePersoon(persoon);
        School deSchool = schoolServiceImpl.getOneSchool(school_id);
        Stagiair nieuweStagiair = new Stagiair(deSchool, persoon, studierichting);
        stagiairService.saveStagiair(nieuweStagiair);
        return "redirect:/home";
    }

    public class SearchedListObject {
        public List<Stagiair> stagiaires;
        public double aantalPaginas;


        public SearchedListObject(List<Stagiair> stagiaires, double aantalPaginas) {
            this.stagiaires = stagiaires;
            this.aantalPaginas = aantalPaginas;
        }
    }

    @RequestMapping(value = "/stagiairesSearch")
    @ResponseBody
    public SearchedListObject searchStagiairesByName(@RequestParam("zoekTerm") String zoekTerm, @RequestParam("paginaNummer") Integer paginaNumer){
        int queryOffset = 0;
        if (paginaNumer != 1){
            queryOffset = (paginaNumer * 10) - 10;
        }

        List<Stagiair> stagiaires = stagiairServiceImpl.getStagiairesBySearch(zoekTerm, queryOffset);
        double aantalPaginas = stagiairServiceImpl.getAantalPaginasSearch(zoekTerm);
        SearchedListObject searchedListObject = new SearchedListObject(stagiaires, aantalPaginas);
        return searchedListObject;
    }

    
    @RequestMapping(value="/stagiairesFilter", method = RequestMethod.POST)
    public String showAllStagiairesFiltered(Integer categorie_id, Integer school_id, RedirectAttributes attr) {
        if(school_id <= 0 && categorie_id > 0){
            attr.addFlashAttribute("stagiaires", stagiairServiceImpl.getAllStagiairesFCategorie(categorie_id));
            attr.addFlashAttribute("geselecteerde_categorie", categorieServiceImpl.getCategorieByID(categorie_id));

            return "redirect:stagiaires";
        }
        else if (categorie_id <= 0 && school_id > 0){
            attr.addFlashAttribute("stagiaires", stagiairServiceImpl.getAllStagiairesFSchool(school_id));
            attr.addFlashAttribute("geselecteerde_school", categorieServiceImpl.getCategorieByID(school_id));

            return "redirect:stagiaires";
        }
        else if (categorie_id <= 0 && school_id <= 0){
            return "redirect:stagiaires";
        }
        else{
            attr.addFlashAttribute("stagiaires", stagiairServiceImpl.getAllStagiairesFCategorieSchool(categorie_id, school_id));
            attr.addFlashAttribute("geselecteerde_school", categorieServiceImpl.getCategorieByID(school_id));
            attr.addFlashAttribute("geselecteerde_categorie", categorieServiceImpl.getCategorieByID(categorie_id));

            return "redirect:stagiaires";
        }

    }


}
