package be.ift.controllers;

import be.ift.domain.Begeleider;
import be.ift.domain.Rol;
import be.ift.domain.Stagiair;
import be.ift.services.servicesimpl.BegeleidingServiceImpl;
import be.ift.services.servicesimpl.StagiairServiceImpl;
import be.ift.services.servicesimpl.UserAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

// Deze controller zal met behulp van @ControllerAdvice data aan elke pagina meegeven.
@ControllerAdvice
public class HeaderController {

    private UserAccountServiceImpl userAccountServiceImpl;
    private StagiairServiceImpl stagiairServiceImpl;

    @Autowired
    public void setUserAccountServiceImpl(UserAccountServiceImpl userAccountServiceImpl) {
        this.userAccountServiceImpl = userAccountServiceImpl;
    }

    @Autowired void setStagiairServiceImpl(StagiairServiceImpl stagiairServiceImpl) {
        this.stagiairServiceImpl = stagiairServiceImpl;
    }

    // Indien een gebruiker een begeleider is zal zijn lijst met stagiaires toegevoegd worden
    // Zoniet zal een lege lijst meegegeven worden
    @ModelAttribute
    public void addStagiairList(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        if(auth.getName() == "anonymousUser"){

        }
        else{
            String email = auth.getName(); //get logged in username
            Rol rol = userAccountServiceImpl.getRolByEmail(email);
            Boolean isBegeleider = false;

            List<Stagiair> eigenStagiaires = new ArrayList<>();

            // Controle op de rol van de gebruiker
            // If begeleider => get stagiaires
            switch (rol.getNaam()){
                case "ROLE_BEGELEIDER": case "ROLE_STAGECOORDINATOR":
                    isBegeleider = true;
                    Begeleider begeleider = userAccountServiceImpl.getBegeleiderByEmail(email);
                    if(begeleider == null)
                    {
                        isBegeleider = false;
                        break;
                    }
                    else {
                        eigenStagiaires = stagiairServiceImpl.getStagiairesByBegeleider(begeleider.getId());
                    }

                    break;
                case "ROLE_HR":
                    break;
                case "ROLE_GUEST":
                    break;
            }
            model.addAttribute("eigenStagiaires", eigenStagiaires);
            model.addAttribute("isBegeleider", isBegeleider);
        }
    }
}

