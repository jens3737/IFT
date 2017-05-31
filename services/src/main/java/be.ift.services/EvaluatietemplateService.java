package be.ift.services;

import be.ift.domain.Evaluatietemplate;

/**
 * Created by JAHBD44 on 5/04/2017.
 */
public interface EvaluatietemplateService {
    /*GET*/
    Evaluatietemplate getOneEvaluatieTemplate(int id);

    /*CREATE*/
    Evaluatietemplate saveTemplate(Evaluatietemplate evaluatietemplate);
}
