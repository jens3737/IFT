package be.ift.services.servicesimpl;

import be.ift.domain.Begeleider;
import be.ift.domain.Stage;
import be.ift.repositories.BegeleiderRepository;
import be.ift.services.BegeleiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JHNBD42 on 8/03/2017.
 */
@Service
public class BegeleiderServiceImpl implements BegeleiderService {

    @Autowired
    public BegeleiderRepository begeleiderRepository;

    // Pagesize voor pagination
    private final static int PAGESIZE = 10;

    // Lijst met ALLE begeleiders ophalen
    @Override
    public List<Begeleider> getAllBegeleiders() {
        List<Begeleider> begeleiders = new ArrayList<>();
        begeleiderRepository.findAll()
                .forEach(begeleiders::add);
        return begeleiders;
    }

    // Lijst alle begeleiders ophalen - Pagination versie
    @Override
    public List<Begeleider> getAllBegeleiders(int pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, PAGESIZE, Sort.Direction.ASC, "id");
        return begeleiderRepository.findAll(request).getContent();
    }

    // Lijst begeleiders per categorie
    @Override
    public List<Begeleider> getAllBegeleidersFCategorie(Integer CAT_ID) {
        return begeleiderRepository.findAllBegeleidersFCategorie(CAT_ID);
    }

    // Lijst met stages
    @Override
    public List<Stage> getCurrentStagesByID(Integer id) {
        return begeleiderRepository.findAllCurrentStagesByID(id);
    }




    @Override
    public Begeleider getOneBegeleider(Integer id) {
        return begeleiderRepository.findOne(id);
    }


    @Override
    public Begeleider saveBegeleider(Begeleider begeleider) {
        return begeleiderRepository.save(begeleider);
    }


    @Override
    public double getAantalPaginas() {
        double aantalRecords = (double) begeleiderRepository.count();
        return Math.ceil(aantalRecords / PAGESIZE);
    }

    /*Filter op categorie */
    @Override
    public List<Begeleider> getAllBegeleidersFCategorie(Integer categorieId, Integer queryOffset) {

        List<Begeleider> begeleiders = begeleiderRepository.findByCategorieId(categorieId, queryOffset);
        return begeleiders;
    }

    @Override
    public double getAantalPaginasFCategorie(Integer CAT_ID) {
        double aantalRecords = (double) begeleiderRepository.countByCategorie(CAT_ID);
        return Math.ceil(aantalRecords / PAGESIZE);
    }

    /*Search*/
    @Override
    public double getAantalPaginasSearch(String naam) {
        double aantalRecords = (double) begeleiderRepository.countBySearch(makeWildcard(naam));
        return Math.ceil(aantalRecords / PAGESIZE);
    }

    public String makeWildcard(String naam){
        StringBuilder str = new StringBuilder(naam);
        str.insert(0,'%');
        str.insert(str.length(), '%');
        return str.toString();
    }

    @Override
    public List<Begeleider> getBegeleidersBySearch(String naam, Integer queryOffset) {
        return begeleiderRepository.getBegeleiderBySearch(makeWildcard(naam), queryOffset);
    }

    @Override
    public Begeleider findBegeleiderByEmail(String email) {
        Begeleider begeleider = new Begeleider();
        begeleider = this.begeleiderRepository.findBegeleiderByEmail(email);
        if (begeleider == null) {
            return null;
        }
        else {
            return begeleider;
        }
    }


//    @Override
//    public UserAccount findOneByEmail(String email) {
//        return this.useraccountRepository.findByEmail(email).orElse(null);
//    }
}
