package be.ift.repositories;

import be.ift.domain.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by JHNBD42 on 8/03/2017.
 */

@Repository
public interface SchoolRepository extends PagingAndSortingRepository<School, Integer>{

    @Query(value = "SELECT count(s) FROM School s WHERE s.naam LIKE ?1")
    int countBySearch(String naam);

    @Query(value = "SELECT * " +
            "FROM tblSchool s " +
            "WHERE SCHO_naam " +
            "LIKE ?1 " +
            "LIMIT 10 OFFSET ?2", nativeQuery = true)
    List<School> getSchoolBySearch(String naam, Integer paginaNummer);
}
