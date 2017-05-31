package be.ift.services.servicesimpl;

import be.ift.domain.Categorie;
import be.ift.domain.Stage;
import be.ift.services.StageopdrachtService;
import be.ift.domain.Stageopdracht;
import be.ift.repositories.StageopdrachtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by JHNBD42 on 6/03/2017.
 */
@Service
public class StageopdrachtServiceImpl implements StageopdrachtService {
    private StageopdrachtRepository stageopdrachtRepository;
    private final static int PAGESIZE = 10;

    @Autowired
    public void setStageopdrachtRepository(StageopdrachtRepository stageodrachtRepository){
        this.stageopdrachtRepository = stageodrachtRepository;
    }

    @Override
    public Stageopdracht saveStageopdracht(Stageopdracht stageopdracht) {
        return stageopdrachtRepository.save(stageopdracht);
    }

    @Override
    public List<Stageopdracht> getAllStageopdrachten() {
        List<Stageopdracht> stageopdrachten = new ArrayList<>();
        stageopdrachtRepository.findAll()
                .forEach(stageopdrachten::add);

        return stageopdrachten;
    }

    @Override
    public List<Stageopdracht> getAllStageopdrachtenFCategorie(Integer CAT_ID) {
        List<Stageopdracht> stageopdrachten = new ArrayList<>();
        stageopdrachten=stageopdrachtRepository.findAllStageopdrachtenFCategorie(CAT_ID);
                //.forEach(stageopdrachten::add);

        return stageopdrachten;
    }

    @Override
    public Stageopdracht getOneStageopdracht(int id){
        return stageopdrachtRepository.findOne(id);
    }

    @Override
    public List<Stageopdracht> getAllStageopdrachten(int pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, PAGESIZE, Sort.Direction.ASC, "id");
        return stageopdrachtRepository.findAll(request).getContent();
    }

    @Override
    public double getAantalPaginas() {
        double aantalRecords = (double) stageopdrachtRepository.count();
        return Math.ceil(aantalRecords / PAGESIZE);
    }

    @Override
    public List<Stageopdracht> getAllStageopdrachtenFCategorie(Integer categorieId, Integer queryOffset) {

        List<Stageopdracht> stageopdrachts = stageopdrachtRepository.findByCategorieId(categorieId, queryOffset);
        return stageopdrachts;
    }

    @Override
    public double getAantalPaginasFCategorie(Integer CAT_ID) {
        double aantalRecords = (double) stageopdrachtRepository.countByCategorie(CAT_ID);
        return Math.ceil(aantalRecords / PAGESIZE);
    }

    /*Search*/
    @Override
    public double getAantalPaginasSearch(String naam) {
        double aantalRecords = (double) stageopdrachtRepository.countBySearch(makeWildcard(naam));
        return Math.ceil(aantalRecords / PAGESIZE);
    }

    public String makeWildcard(String naam){
        StringBuilder str = new StringBuilder(naam);
        str.insert(0,'%');
        str.insert(str.length(), '%');
        return str.toString();
    }

    @Override
    public List<Stageopdracht> getStageopdrachtenBySearch(String naam, Integer queryOffset) {
        return stageopdrachtRepository.getStageopdrachtBySearch(makeWildcard(naam), queryOffset);
    }

    @Override
    public List<Stage> getLopendeStages(Integer StageopdrachtId) {
        return stageopdrachtRepository.getLopendeStages(StageopdrachtId);
    }

    @Override
    public double aantalStageopdrachten() {
        return stageopdrachtRepository.count();
    }
}
