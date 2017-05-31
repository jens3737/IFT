package be.ift.repositories;

import be.ift.domain.Evaluatietemplate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by JAHBD44 on 5/04/2017.
 */
@Repository
public interface EvaluatietemplateRepository extends CrudRepository<Evaluatietemplate, Integer> {

    @Query("Select e from Evaluatietemplate e where e.id = ?1")
    Evaluatietemplate findEvaluatietemplateByID(Integer TEMP_ID);

    Evaluatietemplate save(Evaluatietemplate evaluatietemplate);
}
