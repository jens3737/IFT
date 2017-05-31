package be.ift.repositories;

import be.ift.domain.Begeleiding;
import be.ift.domain.Stage;
import be.ift.domain.Stagiair;
import groovy.time.BaseDuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by JHNBD42 on 8/03/2017.
 */
@Repository
public interface StagiairRepository extends PagingAndSortingRepository<Stagiair, Integer> {
    /* RETURNED Lijst van stagiaires gefilterd*/
    @Query("select s from Stagiair s WHERE s.school.id = ?1")
    List<Stagiair> findAllStagiairesFSchool(Integer SHO_ID);

    @Query("select s.stagiair from Stage s WHERE s.stageopdracht.categorie.id  = ?1")
    List<Stagiair> findAllStagiairesFCategorie(Integer CAT_ID);

    @Query("select s.stagiair from Stage s WHERE s.stageopdracht.categorie.id = ?1 AND s.stagiair.school.id = ?2")
    List<Stagiair> findAllStagiairesFCategorieSchool(Integer CAT_ID, Integer SCHO_ID);

    @Query("select s FROM Stage s where s.stagiair.id = ?1 AND s.eindDatum > current_date")
    Stage findCurrentStageFromStagiair(Integer id);

    @Query("select s FROM Stage s where s.stagiair.id = ?1 AND s.eindDatum < current_date")
    List<Stage> findOldStageFromStagiair(Integer id);

    @Query("select b FROM Begeleiding b where b.stage.id = ?1")
    Begeleiding findCurrentBegeleidingByStageID(Integer id);

    @Query(value = "SELECT * " +
                    "FROM tblStagiair as s " +
                        "INNER JOIN tblStage as st " +
                                "ON s.STA_ID = st.STAGE_ID_stagiair " +
                        "INNER JOIN tblStageopdracht as op " +
                                "ON st.STAGE_ID_opdracht = op.OPDR_ID " +
                    "WHERE op.OPDR_ID_categorie = ?1 " +
                    "LIMIT 10 OFFSET ?2", nativeQuery = true)
    List<Stagiair> findAllStagiairesFCategorie(Integer CAT_ID, Integer queryOffset);

    @Query(value = "SELECT * " +
            "FROM tblStagiair as s " +
            "WHERE s.STA_ID_school = ?1 " +
            "LIMIT 10 OFFSET ?2", nativeQuery = true)
    List<Stagiair> findAllStagiairesFSchool(Integer SCHO_ID, Integer queryOffset);

    @Query(value = "SELECT * " +
            "FROM tblStagiair as s " +
            "INNER JOIN tblStage as st " +
                "ON s.STA_ID = st.STAGE_ID_stagiair " +
            "INNER JOIN tblStageopdracht as op " +
                "ON st.STAGE_ID_opdracht = op.OPDR_ID " +
            "WHERE s.STA_ID_school = ?1 AND op.OPDR_ID_categorie = ?2 " +
            "LIMIT 10 OFFSET ?3", nativeQuery = true)
    List<Stagiair> findAllStagiairesFCategorieSchool(Integer SCHO_ID, Integer CAT_ID, Integer queryOffset);

    @Query(value = "SELECT COUNT(s.stagiair) FROM Stage s WHERE s.stageopdracht.categorie.id = ?1")
    int countByCategorie(Integer CAT_ID);

    @Query(value = "SELECT COUNT(s) FROM Stagiair s WHERE s.school.id = ?1")
    int countBySchool(Integer SCHO_ID);

    @Query(value = "SELECT COUNT(s) FROM Stage s WHERE s.stagiair.school.id = ?1 AND s.stageopdracht.categorie.id = ?2")
    int countBySchoolCategorie(Integer SCHO_ID, Integer CAT_ID);

    @Query(value = "SELECT count(s) FROM Stagiair s WHERE s.persoon.naam LIKE ?1")
    int countBySearch(String naam);



    @Query(value = "SELECT * " +
            "FROM tblPersoon p " +
            "INNER JOIN tblStagiair s " +
                    "ON s.STA_ID_persoon = p.PER_ID " +
            "WHERE PER_voornaam_naam " +
                  "LIKE ?1 " +
            "LIMIT 10 OFFSET ?2", nativeQuery = true)
    List<Stagiair> getStagiairBySearch(String naam, Integer paginaNummer);

    @Query(value = "SELECT b.stage.stagiair FROM Begeleiding b WHERE b.begeleider.id = ?1 AND b.stage.eindDatum > current_date")
    List<Stagiair> getBegeleiddeStagiairs(Integer BegID);
}
