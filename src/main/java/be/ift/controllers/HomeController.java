package be.ift.controllers;

import be.ift.MailService;
import be.ift.domain.Begeleider;
import be.ift.domain.Rol;
import be.ift.domain.Stagiair;
import be.ift.services.StagiairService;
import be.ift.services.UserAccountService;
import be.ift.services.servicesimpl.HashingServiceImpl;
import be.ift.services.servicesimpl.UserAccountServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JHNBD42 on 30/03/2017.
 */
@Controller
public class HomeController {
    private StagiairService stagiairService;
    private UserAccountServiceImpl userAccountServiceImpl;
    private HashingServiceImpl hashingServiceImpl;


    @Autowired
    public void setStagiairService(StagiairService stagiairService){
        this.stagiairService = stagiairService;
    }
    @Autowired
    public void setHashingServiceImpl(HashingServiceImpl hashingServiceImpl){
        this.hashingServiceImpl = hashingServiceImpl;
    }
    @Autowired
    public void setUserAccountService(UserAccountServiceImpl userAccountServiceImpl){
        this.userAccountServiceImpl = userAccountServiceImpl;
    }



    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
    public String showHome(Model model){
//        System.out.println(hashingServiceImpl.passwordEncoder.encode("q"));
//        System.out.println(hashingServiceImpl.passwordEncoder.encode("changeme"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); //get logged in username
        Rol rol = userAccountServiceImpl.getRolByEmail(email);
        Boolean isBegeleider = false;
        Boolean isLeeg = false;

        System.out.println("Hello World!");
        List<Stagiair> stagiaires = new ArrayList<>();

        switch (rol.getNaam()){
            case "ROLE_BEGELEIDER": case "ROLE_STAGECOORDINATOR":
                isBegeleider = true;
                Begeleider begeleider = userAccountServiceImpl.getBegeleiderByEmail(email);
                if(begeleider == null)
                {
                    isBegeleider = false;
                    break;
                }
                else
                {
                    stagiaires = stagiairService.getStagiairesByBegeleider(begeleider.getId());
                }

                break;
            case "ROLE_HR":
                break;
            case "ROLE_GUEST":
                break;
        }

        model.addAttribute("stagiaires", stagiaires);
        model.addAttribute("isBegeleider", isBegeleider);
        return "/home";
    }

}