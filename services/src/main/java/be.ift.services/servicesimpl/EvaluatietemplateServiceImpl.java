package be.ift.services.servicesimpl;

import be.ift.domain.Evaluatietemplate;
import be.ift.repositories.EvaluatietemplateRepository;
import be.ift.services.EvaluatietemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by JAHBD44 on 5/04/2017.
 */
@Service
public class EvaluatietemplateServiceImpl implements EvaluatietemplateService {
    @Autowired
    public EvaluatietemplateRepository evaluatietemplateRepository;

    @Override
    public Evaluatietemplate saveTemplate(Evaluatietemplate evaluatietemplate) {
        return evaluatietemplateRepository.save(evaluatietemplate);
    }

    @Override
    public Evaluatietemplate getOneEvaluatieTemplate(int id) {
        return evaluatietemplateRepository.findEvaluatietemplateByID(id);
    }


}
