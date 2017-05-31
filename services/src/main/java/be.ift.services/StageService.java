package be.ift.services;

import be.ift.domain.Stage;

/**
 * Created by VXFBD43 on 19/03/2017.
 */
public interface StageService {

    Stage saveStage(Stage stage);
    Stage getOneStage(Integer id);
    double aantalStages();

}
