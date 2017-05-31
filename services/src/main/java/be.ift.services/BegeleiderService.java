package be.ift.services;

import be.ift.domain.Begeleider;
import be.ift.domain.Stage;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by JHNBD42 on 8/03/2017.
 */
public interface BegeleiderService {
    List<Begeleider> getAllBegeleiders();
    List<Begeleider> getAllBegeleidersFCategorie(Integer CAT_ID);
    Begeleider getOneBegeleider(Integer id);
    List<Stage> getCurrentStagesByID(Integer id);
    Begeleider saveBegeleider(Begeleider begeleider);


    double getAantalPaginas();
    List<Begeleider> getAllBegeleiders(int pageNumber);
    double getAantalPaginasFCategorie(Integer CAT_ID);
    List<Begeleider> getAllBegeleidersFCategorie(Integer CAT_ID, Integer queryOffset);


    double getAantalPaginasSearch(String naam);
    List<Begeleider> getBegeleidersBySearch(String naam, Integer paginaNummer);
    Begeleider findBegeleiderByEmail(String email);

}
