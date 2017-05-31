package be.ift.services.servicesimpl;

import be.ift.domain.Stage;
import be.ift.repositories.StageRepository;
import be.ift.services.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by VXFBD43 on 19/03/2017.
 */
@Service
public class StageServiceImpl implements StageService{

    @Autowired
    StageRepository stageRepository;

    @Override
    public Stage saveStage(Stage stage) {
        return stageRepository.save(stage);
    }

    @Override
    public Stage getOneStage(Integer id) {return stageRepository.findOne(id);}

    @Override
    public double aantalStages() {
        return stageRepository.count();
    }

}
