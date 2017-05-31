package be.ift.repositories;

import be.ift.domain.Categorie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by JHNBD42 on 7/03/2017.
 */
@Repository
public interface CategorieRepository extends CrudRepository<Categorie, Integer> {

    @Query("Select c from Categorie c where c.id = ?1")
    Categorie findCategorieByID(Integer CAT_ID);
}
