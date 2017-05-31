package be.ift.controllers;

import be.ift.domain.School;
import be.ift.domain.Persoon;
import be.ift.domain.School;
import be.ift.services.PersoonService;
import be.ift.services.SchoolService;
import be.ift.services.servicesimpl.SchoolServiceImpl;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * Created by JHNBD42 on 11/03/2017.
 */
@Controller
public class SchoolController {

    private SchoolServiceImpl schoolServiceImpl;
    private PersoonService persoonService;
    private SchoolService schoolService;

    /*------------------------------WIRING ------------------------- */
    @Autowired
    public void setSchoolServiceImpl(SchoolServiceImpl schoolServiceImpl){this.schoolServiceImpl = schoolServiceImpl;}
    @Autowired
    public void setPersoonService(PersoonService persoonService){
        this.persoonService = persoonService;
    }
    @Autowired
    public void setSchoolService(SchoolService schoolService){this.schoolService = schoolService;}


    /*------------------------------GET ------------------------- */
    @RequestMapping("/school")
    public String newSchool(Model model){
        modelAttributes(model); //methode beneden. KIS
        return "schoolcreate";
    }

    @RequestMapping(value = "/school/{id}", method = RequestMethod.GET)
    public String showSchool(@PathVariable int id, Model model)
    {
        model.addAttribute("school", schoolServiceImpl.getOneSchool(id));
        return "schoolshow";
    }

    @RequestMapping(value = "/scholen", method = RequestMethod.GET)
    public String viewScholen(@RequestParam(name = "p", defaultValue = "1") int pageNumber, Model model) {
        double aantalPaginas = schoolServiceImpl.getAantalPaginas();
        List<School> scholen = schoolServiceImpl.getAllScholen(pageNumber);
        model.addAttribute("scholen", scholen);
        model.addAttribute("aantalPaginas", aantalPaginas);
        model.addAttribute("huidigePagina", pageNumber);

        return "scholen";
    }

    @RequestMapping(value = "/scholenpage", method = RequestMethod.GET)
    @ResponseBody
    public List<School> ajaxTest(@RequestParam(name = "p", defaultValue = "1") int pageNumber) {
        List<School> scholen = schoolServiceImpl.getAllScholen(pageNumber);
        return scholen;

    }

    /*------------------------------POST ------------------------- */
    public class SearchedListObject {
        public List<School> scholen;
        public double aantalPaginas;


        public SearchedListObject(List<School> scholen, double aantalPaginas) {
            this.scholen = scholen;
            this.aantalPaginas = aantalPaginas;
        }
    }

    @RequestMapping(value = "/scholenSearch")
    @ResponseBody
    public SearchedListObject searchStagiairesByName(@RequestParam("zoekTerm") String zoekTerm, @RequestParam("paginaNummer") Integer paginaNumer){
        System.out.println("zoekTerm: " + zoekTerm);
        System.out.println("paginaNummer: " + paginaNumer);
        int queryOffset = 0;
        if (paginaNumer != 1){
            queryOffset = (paginaNumer * 10) - 10;
        }

        List<School> scholen = schoolServiceImpl.getScholenBySearch(zoekTerm, queryOffset);
        double aantalPaginas = schoolServiceImpl.getAantalPaginasSearch(zoekTerm);
        SearchedListObject searchedListObject = new SearchedListObject(scholen, aantalPaginas);
        return searchedListObject;
    }

    @RequestMapping(value = "/schoolopslaan", method = RequestMethod.POST)
    public String saveSchool(School school, String persoonnaam, String persoonmail, String persoonnr){

        Persoon persoon = new Persoon(persoonnaam,persoonmail,persoonnr);
        persoonService.savePersoon(persoon);
        school.setContactpersoon(persoon);
        schoolService.saveSchool(school);
        return "redirect:/home";
    }

    /*------------------------------METHODS ------------------------- */

    public void modelAttributes(Model model){
        String persoonnaam = "";
        String persoonmail = "";
        String persoonnr = "";
        model.addAttribute("persoonnaam", persoonnaam);
        model.addAttribute("persoonmail", persoonmail);
        model.addAttribute("persoonnr", persoonnr);
        model.addAttribute("school", new School());
        model.addAttribute("scholenlijst", schoolService.getAllScholen());
    }
}
