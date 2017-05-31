package be.ift.repositories;

import be.ift.domain.Antwoord;
import be.ift.domain.Stagiair;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by JHNBD42 on 8/05/2017.
 */
@Repository
public interface AnalyseRepository extends CrudRepository<Stagiair, Integer> {

    @Query(value = "SELECT * " +
            "FROM tblStagiair as s " +
            "INNER JOIN tblStage as st " +
            "ON s.STA_ID = st.STAGE_ID_stagiair " +
            "INNER JOIN tblStageopdracht as op " +
            "ON st.STAGE_ID_opdracht = op.OPDR_ID " +
            "WHERE op.OPDR_ID_categorie = ?2 AND s.STA_ID_school = ?1 ", nativeQuery = true)
    List<Stagiair> findAllStagiairesBySchool(Integer schoolID, Integer categorieID);

}
