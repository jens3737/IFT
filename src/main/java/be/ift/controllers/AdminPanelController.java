package be.ift.controllers;

import be.ift.domain.*;
import be.ift.repositories.*;
import be.ift.services.CategorieService;
import be.ift.services.*;
import be.ift.services.servicesimpl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AdminPanelController {
    private BegeleiderServiceImpl begeleiderServiceImpl;
    private CategorieServiceImpl categorieServiceImpl;
    private PersoonServiceImpl persoonServiceImpl;
    private RolServiceImpl rolServiceImpl;
    private SchoolServiceImpl schoolServiceImpl;
    private StageopdrachtServiceImpl stageopdrachtServiceImpl;
    private StagiairServiceImpl stagiairServiceImpl;
    private StageServiceImpl stageServiceImpl;
    private UserAccountServiceImpl userAccountServiceImpl;


    @Autowired
    public void setBegeleiderServiceImpl(BegeleiderServiceImpl begeleiderServiceImpl){
        this.begeleiderServiceImpl = begeleiderServiceImpl;
    }

    @Autowired
    public void setCategorieServiceImpl(CategorieServiceImpl categorieServiceImpl) {
        this.categorieServiceImpl = categorieServiceImpl;
    }

    @Autowired
    public void setPersoonServiceImpl(PersoonServiceImpl persoonServiceImpl) {
        this.persoonServiceImpl = persoonServiceImpl;
    }

    @Autowired
    public void setRolServiceImpl(RolServiceImpl rolServiceImpl) {
        this.rolServiceImpl = rolServiceImpl;
    }

    @Autowired
    public void setSchoolServiceImpl(SchoolServiceImpl schoolServiceImpl) {
        this.schoolServiceImpl = schoolServiceImpl;
    }

    @Autowired
    public void setStageopdrachtServiceImpl(StageopdrachtServiceImpl stageopdrachtServiceImpl) {
        this.stageopdrachtServiceImpl = stageopdrachtServiceImpl;
    }

    @Autowired
    public void setStagiairServiceImpl(StagiairServiceImpl stagiairServiceImpl) {
        this.stagiairServiceImpl = stagiairServiceImpl;
    }

    @Autowired
    public void setStageServiceImpl(StageServiceImpl stageServiceImpl) {
        this.stageServiceImpl = stageServiceImpl;
    }

    @Autowired
    public void setUserAccountServiceImpl(UserAccountServiceImpl userAccountServiceImpl) {
        this.userAccountServiceImpl = userAccountServiceImpl;
    }



    @RequestMapping("/admin")
    public String showAdminPanel(Model model){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("StagiairAantal", (int) stagiairServiceImpl.aantalStagiaires());
        model.addAttribute("StageAantal", (int) stageServiceImpl.aantalStages());
        model.addAttribute("StageopdrachtAantal", (int) stageopdrachtServiceImpl.aantalStageopdrachten());
        model.addAttribute("GebruikerAantal", (int) userAccountServiceImpl.aantalUsers());
        model.addAttribute("CurrentUser", (UserAccount) userAccountServiceImpl.findOneByEmail(email));


        return "adminpanel";
    }


    @RequestMapping("/adminpanel")
    public String showAdminPanel2(Model model){
        return "adminpanel";
    }



}
