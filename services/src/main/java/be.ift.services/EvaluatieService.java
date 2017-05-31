package be.ift.services;

import be.ift.domain.Evaluatieformulier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by JAHBD44 on 5/04/2017.
 */

public interface EvaluatieService {

    Evaluatieformulier saveEvaluatieformulier(Evaluatieformulier evaluatieformulier);

    List<Evaluatieformulier> getEvaluatieformulieren(Integer stagiairID);
}

