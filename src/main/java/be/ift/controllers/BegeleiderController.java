package be.ift.controllers;

import be.ift.domain.Begeleider;
import be.ift.domain.Stage;
import be.ift.domain.Categorie;
import be.ift.domain.Persoon;
import be.ift.services.BegeleiderService;
import be.ift.services.PersoonService;
import be.ift.services.servicesimpl.BegeleiderServiceImpl;
import be.ift.services.servicesimpl.CategorieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.misc.Contended;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JHNBD42 on 11/03/2017.
 */
@Controller
public class BegeleiderController {
    private BegeleiderServiceImpl begeleiderServiceImpl;
    private CategorieServiceImpl categorieServiceImpl;
    private PersoonService persoonService;
    private BegeleiderService begeleiderService;

    @Autowired
    public void setBegeleiderServiceImpl(BegeleiderServiceImpl begeleiderServiceImpl){
        this.begeleiderServiceImpl = begeleiderServiceImpl;
    }

    @Autowired
    public void setPersoonService(PersoonService persoonService){
        this.persoonService = persoonService;
    }

    @Autowired
    public void setCategorieServiceImpl(CategorieServiceImpl categorieServiceImpl){
        this.categorieServiceImpl = categorieServiceImpl;
    }

    @Autowired
    public void setBegeleiderService(BegeleiderService begeleiderService){
        this.begeleiderService = begeleiderService;
    }
        /*------------------------------GET ------------------------- */

    @RequestMapping(value= "/begeleider", method = RequestMethod.GET)
    public String newBegeleider(Model model){
//        String functieomschrijving = "";
//        Integer categorie_id = 0;
//        model.addAttribute("persoon", new Persoon() );
//        model.addAttribute("categorieen", categorieServiceImpl.getAllCategories());
//        model.addAttribute("categorie_id", categorie_id );
//        model.addAttribute("functieomschrijving", functieomschrijving);

        return "redirect:/useraccount";
    }

    @RequestMapping(value = "/begeleiders", method = RequestMethod.GET)
    public String viewBegeleiders(@RequestParam(name = "p", defaultValue = "1") int pageNumber, Model model) {

        model.addAttribute("categorieen", categorieServiceImpl.getAllCategories());
        /* standaard*/
        double aantalPaginas = begeleiderServiceImpl.getAantalPaginas();
        if (!model.containsAttribute("begeleiders")) {
            List<Begeleider> begeleiders = begeleiderServiceImpl.getAllBegeleiders(pageNumber);
            model.addAttribute("begeleiders", begeleiders);
            for (Begeleider begeleider : begeleiders){
                System.out.println(begeleider.getPersoon().getNaam());
            }
        }
        model.addAttribute("aantalPaginas", aantalPaginas);
        model.addAttribute("huidigePagina", pageNumber);

        return "begeleiders";
    }

    @RequestMapping(value = "/begeleiderspage", method = RequestMethod.GET)
    @ResponseBody
    public List<Begeleider> getNextPage(@RequestParam(name = "p", defaultValue = "1") int pageNumber) {
        List<Begeleider> begeleiders = begeleiderServiceImpl.getAllBegeleiders(pageNumber);
        return begeleiders;

    }

    public class FilteredListObject {
        public List<Begeleider> begeleiders;
        public double aantalPaginas;
        public Integer categorieID;

        public FilteredListObject(List<Begeleider> begeleiders, double aantalPaginas, Integer categorieID) {
            this.begeleiders = begeleiders;
            this.aantalPaginas = aantalPaginas;
            this.categorieID = categorieID;
        }
    }

    @RequestMapping(value = "/begeleidersFilter", method = RequestMethod.GET)
    @ResponseBody
    public FilteredListObject getFilteredPage(@RequestParam(name = "p", defaultValue = "1") int pageNumber, @RequestParam(name ="fc", defaultValue = "0") int catID) {
        int queryOffset = 0;
        if (pageNumber != 1){
            queryOffset = (pageNumber * 10) - 10;
        }
        System.out.println(pageNumber);
        System.out.println(queryOffset);

        Categorie categorie = categorieServiceImpl.getCategorieByID(catID);
        List<Begeleider> begeleiders = begeleiderServiceImpl.getAllBegeleidersFCategorie(catID, queryOffset);
        double aantalPaginas = begeleiderServiceImpl.getAantalPaginasFCategorie(catID);

        FilteredListObject filteredListObject = new FilteredListObject(begeleiders, aantalPaginas, catID);
        return filteredListObject;
    }

    /*-------------------POST-----------------------*/
    @RequestMapping("/begeleider/{id}")
    public String showBegeleider(@PathVariable int id, Model model) {

        if(begeleiderServiceImpl.getOneBegeleider(id) != null) {

            DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
            Begeleider begeleider = begeleiderServiceImpl.getOneBegeleider(id);
            String geboorteDatum = outputFormatter.format(begeleider.getPersoon().getGeboorteDatum());
            model.addAttribute("begeleider", begeleider);
            model.addAttribute("geboorteDatum", geboorteDatum);
            List<Stage> stages = begeleiderServiceImpl.getCurrentStagesByID(id);
            model.addAttribute("stages", stages);

            if (stages.isEmpty()) {
                return "begeleidershow";
            }
//        else {
//            List<String> startData = new ArrayList<>();
//            List<String> eindData = new ArrayList<>();
//            for(Stage stage : stages) {
//                startData.add(outputFormatter.format(stage.getStartDatum()));
//                eindData.add(outputFormatter.format(stage.getEindDatum()));
//            }
//            model.addAttribute("startData", startData);
//            model.addAttribute("eindData", eindData);
//        }
            return "begeleidershow";

        }
        else{
            return "redirect:/404";
        }
    }

    /*------------------------------POST ------------------------- */

    public class SearchedListObject {
        public List<Begeleider> begeleiders;
        public double aantalPaginas;


        public SearchedListObject(List<Begeleider> begeleiders, double aantalPaginas) {
            this.begeleiders = begeleiders;
            this.aantalPaginas = aantalPaginas;
        }
    }

    @RequestMapping(value = "/begeleidersSearch")
    @ResponseBody
    public SearchedListObject searchStagiairesByName(@RequestParam("zoekTerm") String zoekTerm, @RequestParam("paginaNummer") Integer paginaNumer){
        System.out.println("zoekTerm: " + zoekTerm);
        System.out.println("paginaNummer: " + paginaNumer);
        int queryOffset = 0;
        if (paginaNumer != 1){
            queryOffset = (paginaNumer * 10) - 10;
        }

        List<Begeleider> begeleiders = begeleiderServiceImpl.getBegeleidersBySearch(zoekTerm, queryOffset);
        double aantalPaginas = begeleiderServiceImpl.getAantalPaginasSearch(zoekTerm);
        SearchedListObject searchedListObject = new SearchedListObject(begeleiders, aantalPaginas);
        return searchedListObject;
    }

    //Momenteel buiten gebruik. (dubbele content, zie useraccount)
    @RequestMapping(value = "/begeleideropslaan", method = RequestMethod.POST)
    public String saveBegeleider(Persoon persoon, Integer categorie_id, String functieomschrijving){

        persoonService.savePersoon(persoon);
        Categorie categorie = categorieServiceImpl.getCategorieByID(categorie_id);
        Begeleider begeleider = new Begeleider(persoon, functieomschrijving, categorie);
        begeleiderService.saveBegeleider(begeleider);
        return "redirect:/home";
    }

    @RequestMapping(value="/begeleidersFilter", method = RequestMethod.POST)
    public String showAllBegeleidersFCategorie(Integer categorie_id, RedirectAttributes attr){
        if(categorie_id >0){
            attr.addFlashAttribute("begeleiders", begeleiderServiceImpl.getAllBegeleidersFCategorie(categorie_id));
            attr.addFlashAttribute("geselecteerde_categorie", categorieServiceImpl.getCategorieByID(categorie_id));

            return "redirect:begeleiders";
        }
        return "redirect:begeleiders";
    }
}
