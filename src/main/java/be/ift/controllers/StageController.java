package be.ift.controllers;

import be.ift.domain.*;
import be.ift.repositories.StageopdrachtRepository;
import be.ift.services.*;
import be.ift.services.servicesimpl.BegeleiderServiceImpl;
import be.ift.services.servicesimpl.StageServiceImpl;
import be.ift.services.servicesimpl.StageopdrachtServiceImpl;
import be.ift.services.servicesimpl.StagiairServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by JAHBD44 on 16/03/2017.
 */
@Controller
public class StageController {

    private StageopdrachtService stageopdrachtService;
    private StageService stageService;
    private StageServiceImpl stageServiceImpl;
    private StageopdrachtServiceImpl stageopdrachtServiceImpl;
    private BegeleiderService begeleiderService;
    private BegeleiderServiceImpl begeleiderServiceImpl;
    private StagiairService stagiairService;
    private StagiairServiceImpl stagiairServiceImpl;
    private BegeleidingService begeleidingService;
    /*------------------------------Wiring ------------------------- */
    @Autowired
    public void setStagiairService(StagiairService stagiairService) {this.stagiairService = stagiairService;}
    @Autowired
    public void setStageopdrachtService(StageopdrachtService stageopdrachtService){this.stageopdrachtService = stageopdrachtService;}
    @Autowired
    public void setBegeleiderServiceImpl(BegeleiderServiceImpl begeleiderServiceImpl){this.begeleiderServiceImpl = begeleiderServiceImpl;}
    @Autowired
    public void setStagiairServiceImpl(StagiairServiceImpl stagiairServiceImpl) {this.stagiairServiceImpl = stagiairServiceImpl;}
    @Autowired
    public void setStageopdrachtServiceImpl(StageopdrachtServiceImpl stageopdrachtServiceImpl){this.stageopdrachtServiceImpl = stageopdrachtServiceImpl;}
    @Autowired
    public void setStageService(StageService stageService){this.stageService = stageService;}
    @Autowired
    public void setBegeleidingService(BegeleidingService begeleidingService) {this.begeleidingService = begeleidingService;}
    /*------------------------------GET ------------------------- */
    @RequestMapping("/stage")
    public String newStage(Model model) {
        modelattributes(model);
        return "stagecreate";
    }
    /*------------------------------POST ------------------------- */

    @RequestMapping(value = "/stageopslaan", method = RequestMethod.POST)
    public String saveStage(@Valid Stage stage, BindingResult result, Integer stagiair_id, Integer opdracht_id, Integer begeleider_id ){


        /*Mega vreemde implementatie --> geeft errors, Jens snapt er hier niks van...*/
        /*Indien result.hasErrors --> 403 pagina???? -crazy*/
//        if (result.hasErrors()) {
//            Stagiair stagiair = stagiairServiceImpl.getOneStagiair(stagiair_id);
//            stage.setStagiair(stagiair);
//            Stageopdracht stageopdracht = stageopdrachtServiceImpl.getOneStageopdracht(opdracht_id);
//            stage.setStageopdracht(stageopdracht);
//            stageService.saveStage(stage);
//            Begeleider begeleider = begeleiderServiceImpl.getOneBegeleider(begeleider_id);
//            Begeleiding begeleiding = new Begeleiding(begeleider,stage);
//            begeleidingService.saveBegeleiding(begeleiding);
//            return "redirect:/home";
//        }
//
//        else{
//            return "redirect:/403";
//        }


        Stagiair stagiair = stagiairServiceImpl.getOneStagiair(stagiair_id);
        stage.setStagiair(stagiair);
        Stageopdracht stageopdracht = stageopdrachtServiceImpl.getOneStageopdracht(opdracht_id);
        stage.setStageopdracht(stageopdracht);
        stageService.saveStage(stage);
        Begeleider begeleider = begeleiderServiceImpl.getOneBegeleider(begeleider_id);
        Begeleiding begeleiding = new Begeleiding(begeleider,stage);
        begeleidingService.saveBegeleiding(begeleiding);
        return "redirect:/home";


    }

    /*------------------------------METHODS ------------------------- */
    public void modelattributes(Model model){
        int stagiair_id = 0;
        int opdracht_id = 0;
        int begeleider_id = 0;
        model.addAttribute("stage", new Stage());
        model.addAttribute("stagiaires", stagiairService.getAllStagiaires());
        model.addAttribute("stagiair_id", stagiair_id);
        model.addAttribute("stageopdrachten", stageopdrachtService.getAllStageopdrachten());
        model.addAttribute("opdracht_id", opdracht_id);
        model.addAttribute("begeleiders", begeleiderServiceImpl.getAllBegeleiders());
        model.addAttribute("begeleider_id", begeleider_id);
    }
}
