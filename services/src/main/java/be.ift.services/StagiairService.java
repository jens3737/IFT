package be.ift.services;

import be.ift.domain.Begeleiding;
import be.ift.domain.Stage;
import be.ift.domain.Persoon;
import be.ift.domain.Stagiair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StagiairService {
    List<Stagiair> getAllStagiaires();
    List<Stagiair> getAllStagiairesFCategorie(Integer CAT_ID);
    List<Stagiair> getAllStagiairesFCategorieSchool(Integer CAT_ID, Integer SCHO_ID);
    List<Stagiair> getAllStagiairesFSchool(Integer SCHO_ID);
    Stagiair getOneStagiair(int id);
    Stagiair saveStagiair(Stagiair stagiair);
    Stage getCurrentStage(Integer ID);
    List<Stage> getOldStages(Integer ID);
    Begeleiding getCurrentBegeleidingByStageID(Integer ID);

    double getAantalPaginas();
    List<Stagiair> getAllStagiaires(int pageNumber);

    double aantalStagiaires();

    double getAantalPaginasFCategorie(Integer CAT_ID);
    int getAantalStagiairPerCat(Integer CAT_ID);
    double getAantalPaginasFSchool(Integer SCHO_ID);
    double getAantalPaginasFSchoolCategorie(Integer SCHO_ID, Integer CAT_ID);
    List<Stagiair> getAllStagiairesFCategorie(Integer CAT_ID, Integer queryOffset);
    List<Stagiair> getAllStagiairesFSchool(Integer SCHO_ID, Integer queryOffset);
    List<Stagiair> getAllStagiairesFCategorieSchool(Integer SCHO_ID, Integer CAT_ID, Integer queryOffset);

    double getAantalPaginasSearch(String naam);
    List<Stagiair> getStagiairesBySearch(String naam, Integer paginaNummer);

    List<Stagiair> getStagiairesByBegeleider(Integer BegId);

    public int getAantalStagiairesBySchool(Integer SCHO_ID);


}
