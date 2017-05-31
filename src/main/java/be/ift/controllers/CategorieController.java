package be.ift.controllers;

import be.ift.domain.Categorie;
import be.ift.services.CategorieService;
import be.ift.services.servicesimpl.CategorieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class CategorieController {
    private CategorieService categorieService;


    @Autowired
    public void setCategorieService(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @RequestMapping("/categorie")
    public String newCategorie(Model model) {
        model.addAttribute("categorie", new Categorie());
        return "categoriecreate";
    }

    @RequestMapping(value = "/categorieopslaan", method = RequestMethod.POST)

    public String saveCategorie(Categorie categorie) {
        categorieService.saveCategorie(categorie);
        return "redirect:/home";
    }
}