package be.ift.controllers;

import be.ift.domain.Stage;
import be.ift.domain.Stageopdracht;
import be.ift.domain.Categorie;
import be.ift.services.StageService;
import be.ift.services.StageopdrachtService;
import be.ift.services.servicesimpl.CategorieServiceImpl;
import be.ift.services.servicesimpl.StageopdrachtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import be.ift.services.servicesimpl.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StageopdrachtController {
    private CategorieServiceImpl categorieServiceImpl;
    private StageopdrachtServiceImpl stageopdrachtServiceImpl;
    private StageopdrachtService stageopdrachtService;
    private StageService stageService;

    //////////////////////////////WIRING/////////////////////////////////////
    @Autowired
    public void setStageopdrachtServiceImpl(StageopdrachtServiceImpl stageopdrachtServiceImpl) {
        this.stageopdrachtServiceImpl = stageopdrachtServiceImpl;
    }

    @Autowired
    public void setCategorieServiceImpl(CategorieServiceImpl categorieServiceImpl) {
        this.categorieServiceImpl = categorieServiceImpl;
    }

    @Autowired
    public void setStageopdrachtService(StageopdrachtService stageopdrachtService) {
        this.stageopdrachtService = stageopdrachtService;
    }

    @Autowired
    public void setStageService(StageService stageService) {
        this.stageService = stageService;
    }


    //////////////////////////////MAPPING/////////////////////////////////////

    //////////////////////////////GET/////////////////////////////////////
    @RequestMapping("/stageopdracht")
    public String newStageopdracht(Model model) {
        model.addAttribute("stageopdracht", new Stageopdracht());
        model.addAttribute("categorieen", categorieServiceImpl.getAllCategories());
        return "stageopdrachtcreate";
    }

    @RequestMapping(value = "/stageopdracht/{id}", method = RequestMethod.GET)
    public String showStageopdracht(@PathVariable int id, Model model)
    {
        if(stageopdrachtServiceImpl.getOneStageopdracht(id) != null){
            model.addAttribute("stageopdracht", stageopdrachtServiceImpl.getOneStageopdracht(id));
            List<Stage> stages = stageopdrachtServiceImpl.getLopendeStages(id);
            stages.sort((o1, o2) -> o1.getStartDatum().compareTo(o2.getStartDatum()));
            model.addAttribute("stages", stages);
            return "stageopdrachtshow";
        }

        else{
            return "redirect:/404";
        }

    }

    @RequestMapping(value = "/stageopdrachten", method = RequestMethod.GET)
    public String viewStageopdrachten(@RequestParam(name = "p", defaultValue = "1") int pageNumber, Model model) {
        /*filter */
        Integer categorie_id = 0;

        model.addAttribute("alle_stageopdrachten", stageopdrachtServiceImpl.getAllStageopdrachten());
        model.addAttribute("categorieen", categorieServiceImpl.getAllCategories());
        model.addAttribute("categorie", new Categorie());
        model.addAttribute("categorie_id", categorie_id);
        double aantalPaginas = stageopdrachtServiceImpl.getAantalPaginas();
        model.addAttribute("aantalPaginas", aantalPaginas);
        model.addAttribute("huidigePagina", pageNumber);
        if (!model.containsAttribute("stageopdrachten")) {
            List<Stageopdracht> stageopdrachten = stageopdrachtServiceImpl.getAllStageopdrachten(pageNumber);
            model.addAttribute("stageopdrachten", stageopdrachten);
        }

        return "stageopdrachten";
    }

    //wrapper class voor ajax call stageopdrachten + pagina's
    public class ListObject {
        public List<Stageopdracht> stageopdrachten;
        public double aantalPaginas;

        public ListObject(List<Stageopdracht> stageopdrachten, double aantalPaginas) {
            this.stageopdrachten = stageopdrachten;
            this.aantalPaginas = aantalPaginas;
        }
    }

    @RequestMapping(value = "/stageopdrachtenpage", method = RequestMethod.GET)
    @ResponseBody
    public ListObject getNextPage(@RequestParam(name = "p", defaultValue = "1") int pageNumber) {
        List<Stageopdracht> stageopdrachten = stageopdrachtServiceImpl.getAllStageopdrachten(pageNumber);
        double aantalPaginas = stageopdrachtServiceImpl.getAantalPaginas();
        ListObject listObject = new ListObject(stageopdrachten, aantalPaginas);
        return listObject;
    }

    public class FilteredListObject {
        public List<Stageopdracht> stageopdrachten;
        public double aantalPaginas;
        public Integer categorieID;

        public FilteredListObject(List<Stageopdracht> stageopdrachten, double aantalPaginas, Integer categorieID) {
            this.stageopdrachten = stageopdrachten;
            this.aantalPaginas = aantalPaginas;
            this.categorieID = categorieID;
        }
    }

    @RequestMapping(value = "/stageopdrachtenFilter", method = RequestMethod.GET)
    @ResponseBody
    public FilteredListObject getFilteredPage(@RequestParam(name = "p", defaultValue = "1") int pageNumber, @RequestParam(name = "fc", defaultValue = "0") int catID) {
        int queryOffset = 0;
        if (pageNumber != 1) {
            queryOffset = (pageNumber * 10) - 10;
        }
        System.out.println(pageNumber);
        System.out.println(queryOffset);

        Categorie categorie = categorieServiceImpl.getCategorieByID(catID);
        List<Stageopdracht> stageopdrachten = stageopdrachtServiceImpl.getAllStageopdrachtenFCategorie(catID, queryOffset);
        double aantalPaginas = stageopdrachtServiceImpl.getAantalPaginasFCategorie(catID);

        FilteredListObject filteredListObject = new FilteredListObject(stageopdrachten, aantalPaginas, catID);
        return filteredListObject;
    }


    //////////////////////////////POST/////////////////////////////////////
    public class SearchedListObject {
        public List<Stageopdracht> stageopdrachten;
        public double aantalPaginas;


        public SearchedListObject(List<Stageopdracht> stageopdrachten, double aantalPaginas) {
            this.stageopdrachten = stageopdrachten;
            this.aantalPaginas = aantalPaginas;
        }
    }

    @RequestMapping(value = "/stageopdrachtenSearch")
    @ResponseBody
    public SearchedListObject searchStageopdrachtenByName(@RequestParam("zoekTerm") String zoekTerm, @RequestParam("paginaNummer") Integer paginaNumer) {
        System.out.println("zoekTerm: " + zoekTerm);
        System.out.println("paginaNummer: " + paginaNumer);
        int queryOffset = 0;
        if (paginaNumer != 1) {
            queryOffset = (paginaNumer * 10) - 10;
        }

        List<Stageopdracht> stageopdrachten = stageopdrachtServiceImpl.getStageopdrachtenBySearch(zoekTerm, queryOffset);
        double aantalPaginas = stageopdrachtServiceImpl.getAantalPaginasSearch(zoekTerm);
        SearchedListObject searchedListObject = new SearchedListObject(stageopdrachten, aantalPaginas);
        return searchedListObject;
    }

    @RequestMapping(value = "/stageopdrachtenFilter", method = RequestMethod.POST)

    public String showAllStageopdrachtenFiltered(Integer categorie_id, RedirectAttributes attr) {
        if (categorie_id > 0) {
            attr.addFlashAttribute("stageopdrachten", stageopdrachtServiceImpl.getAllStageopdrachtenFCategorie(categorie_id));
            attr.addFlashAttribute("geselecteerde_categorie", categorieServiceImpl.getCategorieByID(categorie_id));

            return "redirect:stageopdrachten";
        }
        return "redirect:stageopdrachten";
    }

    @RequestMapping(value = "/stageopdrachtopslaan", method = RequestMethod.POST)
    public String saveStageopdracht(Stageopdracht stageopdracht) {
        stageopdrachtService.saveStageopdracht(stageopdracht);
        return "redirect:/home";
    }



}
