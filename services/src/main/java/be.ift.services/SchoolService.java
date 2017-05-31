package be.ift.services;

import be.ift.domain.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by JHNBD42 on 8/03/2017.
 */

public interface SchoolService {

    /*READ*/
    School getOneSchool(Integer ID);
    List<School> getAllScholen(int pageNumber);
    double getAantalPaginas();
    List<School> getAllScholen();
    double getAantalPaginasSearch(String naam);
    List<School> getScholenBySearch(String naam, Integer paginaNummer);

    /*CREATE*/
    School saveSchool(School school);

    /*UPDATE*/
    School updateSchool(Integer id, School school);
}
