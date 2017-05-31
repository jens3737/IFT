package be.ift.services.servicesimpl;

import be.ift.domain.Evaluatieformulier;
import be.ift.repositories.EvaluatieRepository;
import be.ift.services.EvaluatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by JAHBD44 on 5/04/2017.
 */
@Service
public class EvaluatieServiceImpl implements EvaluatieService {

    @Autowired
    private EvaluatieRepository evaluatieRepository;

    @Override
    public Evaluatieformulier saveEvaluatieformulier(Evaluatieformulier evaluatieformulier) {
        return evaluatieRepository.save(evaluatieformulier);
    }

    @Override
    public List<Evaluatieformulier> getEvaluatieformulieren(Integer stagiairID) {
        return evaluatieRepository.getAllEvaluatiesByStagiairID(stagiairID);
    }




}
