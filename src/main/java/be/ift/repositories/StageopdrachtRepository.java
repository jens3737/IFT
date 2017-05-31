package be.ift.repositories;

import be.ift.domain.Categorie;
import be.ift.domain.Stage;
import be.ift.domain.Stageopdracht;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageopdrachtRepository extends PagingAndSortingRepository<Stageopdracht
        , Integer> {

    @Query("select s from Stageopdracht s WHERE s.categorie.id = ?1")
    List<Stageopdracht> findAllStageopdrachtenFCategorie(Integer CAT_ID);

    @Query("select s from Stageopdracht s WHERE s.categorie.id = ?1")
    List<Stageopdracht> findAllStageopdrachtenFCategorie(Integer CAT_ID, Pageable pageable);

    @Query(value = "SELECT * FROM tblstageopdracht WHERE OPDR_ID_categorie = ?1 LIMIT 10 OFFSET ?2", nativeQuery = true)
    List<Stageopdracht> findByCategorieId(Integer CAT_ID, Integer queryOffset);

    @Query(value = "SELECT COUNT(s) FROM Stageopdracht s WHERE s.categorie.id = ?1")
    int countByCategorie(Integer CAT_ID);

    /*search*/
    @Query(value = "SELECT count(s) FROM Stageopdracht s WHERE s.naam LIKE ?1")
    int countBySearch(String naam);

    @Query(value = "SELECT * " +
            "FROM tblStageopdracht s " +
            "WHERE OPDR_naam " +
            "LIKE ?1 " +
            "LIMIT 10 OFFSET ?2", nativeQuery = true)
    List<Stageopdracht> getStageopdrachtBySearch(String naam, Integer paginaNummer);

    @Query(value = "Select s FROM Stage s WHERE s.stageopdracht.id = ?1 AND s.eindDatum > current_date ")
    List<Stage> getLopendeStages(Integer CatID);
}

