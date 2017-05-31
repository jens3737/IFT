package be.ift.repositories;

import be.ift.domain.Vraag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by JAHBD44 on 29/03/2017.
 */
public interface VraagRepository extends PagingAndSortingRepository<Vraag, Integer> {

    @Query("select v from Vraag v WHERE v.evaluatieTemplate.id = ?1")
    List<Vraag> getAllVragenFEvaluatietemplate(Integer TEMP_ID);
}
