package be.ift.services;

import be.ift.domain.Categorie;
import be.ift.domain.Stage;
import be.ift.domain.Stageopdracht;
import java.util.List;

public interface StageopdrachtService {

    List<Stageopdracht> getAllStageopdrachten();
    Stageopdracht saveStageopdracht(Stageopdracht stageopdracht);
    Stageopdracht getOneStageopdracht(int id);

     /* Returned lijst van stageopdrachten gefilterd op categorie */
    List<Stageopdracht> getAllStageopdrachtenFCategorie(Integer CAT_ID);

    double getAantalPaginas();
    double getAantalPaginasFCategorie(Integer CAT_ID);
    double aantalStageopdrachten();
    List<Stageopdracht> getAllStageopdrachten(int pageNumber);
    List<Stageopdracht> getAllStageopdrachtenFCategorie(Integer CAT_ID, Integer queryOffset);

    /*search*/
    double getAantalPaginasSearch(String naam);
    List<Stageopdracht> getStageopdrachtenBySearch(String naam, Integer paginaNummer);

    List<Stage> getLopendeStages(Integer StageopdrachtId);
}
