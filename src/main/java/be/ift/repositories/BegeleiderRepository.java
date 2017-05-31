package be.ift.repositories;

import be.ift.domain.Begeleider;
import be.ift.domain.Stage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by JHNBD42 on 8/03/2017.
 */
@Repository
public interface BegeleiderRepository extends PagingAndSortingRepository<Begeleider
        , Integer> {

    @Query("SELECT b FROM Begeleider b WHERE b.categorie.id = ?1")
    List<Begeleider> findAllBegeleidersFCategorie(Integer CAT_ID);


    @Query("SELECT b.stage FROM Begeleiding b WHERE b.begeleider.id = ?1 AND b.stage.eindDatum > current_date ")
    List<Stage> findAllCurrentStagesByID(Integer ID);

    @Query(value = "SELECT * FROM tblbegeleider WHERE BEG_ID_categorie = ?1 LIMIT 10 OFFSET ?2", nativeQuery = true)
    List<Begeleider> findByCategorieId(Integer CAT_ID, Integer queryOffset);

    @Query(value = "SELECT COUNT(b) FROM Begeleider b WHERE b.categorie.id = ?1")
    int countByCategorie(Integer CAT_ID);

    @Query(value = "SELECT count(b) FROM Begeleider b WHERE  b.persoon.naam LIKE ?1")
    int countBySearch(String naam);

    @Query(value = "SELECT * " +
            "FROM tblPersoon p " +
            "INNER JOIN tblBegeleider b " +
            "ON b.BEG_ID_persoon = p.PER_ID " +
            "WHERE PER_voornaam_naam " +
            "LIKE ?1 " +
            "LIMIT 10 OFFSET ?2", nativeQuery = true)
    List<Begeleider> getBegeleiderBySearch(String naam, Integer paginaNummer);

    @Query(value = "Select b FROM Begeleider b WHERE b.persoon.email = ?1")
    Begeleider findBegeleiderByEmail(String email);

}
