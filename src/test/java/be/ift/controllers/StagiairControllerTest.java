package be.ift.controllers;

import be.ift.domain.Categorie;
import be.ift.domain.Persoon;
import be.ift.domain.School;
import be.ift.domain.Stagiair;
import be.ift.services.SchoolService;
import be.ift.services.StagiairService;
import be.ift.services.servicesimpl.CategorieServiceImpl;
import be.ift.services.servicesimpl.SchoolServiceImpl;
import be.ift.services.servicesimpl.StagiairServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by VXFBD43 on 20/03/2017.
 */
public class StagiairControllerTest {

    @Mock
    private StagiairService stagiairService;

    @Mock
    private StagiairServiceImpl stagiairServiceImpl;

    @Mock
    private SchoolServiceImpl schoolServiceImpl;

    @Mock
    private CategorieServiceImpl categorieServiceImpl;

    @InjectMocks
    private StagiairController stagiairController;


    private MockMvc mockmvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockmvc = MockMvcBuilders.standaloneSetup(stagiairController).build();
    }


    @Test
    public void newStagiair() throws Exception {
        List<School> scholen = new ArrayList<>();
        scholen.add(new School());
        scholen.add(new School());

        when(schoolServiceImpl.getAllScholen()).thenReturn((List) scholen);

        mockmvc.perform(get("/stagiair"))
                .andExpect(status().isOk())
                .andExpect(view().name("stagiaircreate"))
                .andExpect(model().attribute("scholen", hasSize(2)))
                .andExpect(model().attribute("school_id", is(0)))
                .andExpect(model().attribute("studierichting", is("")))
                .andExpect(model().attribute("persoon", instanceOf(Persoon.class)));

    }

    @Test
    public void viewStagiaires() throws Exception {
        double aantalPaginas = 2;
        int pageNumber = 1;
        Integer categorie_id = 0;
        Integer school_id = 0;
        List<Stagiair> stagiaires = new ArrayList<>();
            stagiaires.add(new Stagiair());
            stagiaires.add(new Stagiair());
        List<Categorie> categorieen = new ArrayList<>();
            categorieen.add(new Categorie());
            categorieen.add(new Categorie());
        List<School> scholen = new ArrayList<>();
            scholen.add(new School());
            scholen.add(new School());


        when(stagiairServiceImpl.getAantalPaginas()).thenReturn((double) aantalPaginas);
        when(categorieServiceImpl.getAllCategories()).thenReturn((List) categorieen);
        when(stagiairServiceImpl.getAllStagiaires(pageNumber)).thenReturn((List) stagiaires);

        mockmvc.perform(get("/stagiaires"))
                .andExpect(status().isOk())
                .andExpect(view().name("stagiaires"))
                .andExpect(model().attribute("aantalPaginas", is(2)))
                .andExpect(model().attribute("huidigePagina", is(1)))
                .andExpect(model().attribute("school_id", is(0)))
                .andExpect(model().attribute("categorie_id", is(0)))
                .andExpect(model().attribute("categorieen", hasSize(2)))
                .andExpect(model().attribute("scholen", hasSize(2)));

    }

//    @RequestMapping(value = "/stagiaires", method = RequestMethod.GET)
//    public String viewStagiaires(@RequestParam(name = "p", defaultValue = "1") int pageNumber, Model model) {
//        /*standaard*/
//        double aantalPaginas = stagiairServiceImpl.getAantalPaginas();
//
//        if (!model.containsAttribute("stagiaires")) {
//            List<Stagiair> stagiaires = stagiairServiceImpl.getAllStagiaires(pageNumber);
//            model.addAttribute("stagiaires", stagiaires);
//        }
//        model.addAttribute("aantalPaginas", aantalPaginas);
//        model.addAttribute("huidigePagina", pageNumber);
//
//        /*filter */
//        Integer categorie_id = 0;
//        Integer school_id = 0;
//
//        model.addAttribute("categorie_id", categorie_id);
//        model.addAttribute("school_id", school_id);
//        model.addAttribute("categorieen", categorieServiceImpl.getAllCategories());
//        model.addAttribute("scholen", schoolServiceImpl.getAllScholen());
//        model.addAttribute("alle_stagiaires", stagiairServiceImpl.getAllStagiaires());
//
//        return "stagiaires";
//    }

}