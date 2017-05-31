package be.ift.services.servicesimpl;

import be.ift.domain.Categorie;
import be.ift.domain.Stageopdracht;
import be.ift.repositories.CategorieRepository;
import be.ift.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JHNBD42 on 7/03/2017.
 */
@Service
public class CategorieServiceImpl implements CategorieService {
    private CategorieRepository categorieRepository;

    @Autowired
    public void setCategorieRepository(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Override
    public Categorie saveCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public List<Categorie> getAllCategories() {
        List<Categorie> categorieen = new ArrayList<>();
        categorieRepository.findAll()
                .forEach(categorieen::add);
        return categorieen;
    }

    @Override
    public Categorie getCategorieByID(Integer CAT_ID){

        return categorieRepository.findOne(CAT_ID);
    }
}
