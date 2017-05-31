package be.ift.repositories;

import be.ift.domain.Evaluatieformulier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by JAHBD44 on 5/04/2017.
 */
@Repository
public interface EvaluatieRepository extends CrudRepository<Evaluatieformulier, Integer> {
    @Query("Select e from Evaluatieformulier e where e.id = ?1")
    Evaluatieformulier findEvaluatieByID(Integer EVAL_ID);

    @Query("SELECT e FROM Evaluatieformulier e WHERE e.stagiair.id = ?1")
    List<Evaluatieformulier> getAllEvaluatiesByStagiairID(Integer stagiairID);
}
