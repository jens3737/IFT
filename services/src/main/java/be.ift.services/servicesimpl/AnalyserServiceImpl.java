package be.ift.services.servicesimpl;

import be.ift.domain.Stagiair;
import be.ift.repositories.AnalyseRepository;
import be.ift.services.AnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by JHNBD42 on 8/05/2017.
 */
@Service
public class AnalyserServiceImpl implements AnalyseService {

    @Autowired
    AnalyseRepository analyseRepository;

    public List<Stagiair> getAllStagiairesPerSchool(Integer schoolID, Integer categorieID){
        return analyseRepository.findAllStagiairesBySchool(schoolID, categorieID);
    }

}
