package be.ift.services.servicesimpl;

import be.ift.domain.Vraag;
import be.ift.repositories.VraagRepository;
import be.ift.services.VraagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JAHBD44 on 29/03/2017.
 */
@Service
public class VraagServiceImpl implements VraagService{
    private final static int PAGESIZE = 10;

    @Autowired
    public VraagRepository vraagRepository;

    @Override
    public List<Vraag> getAllVragen() {
        List<Vraag> vragenlijst = new ArrayList<>();
        vraagRepository.findAll()
                .forEach(vragenlijst::add);
        return vragenlijst;
    }

    @Override
    public List<Vraag> getAllVragenFEvaluatietemplate(Integer TEMP_ID) {
        List<Vraag> vragenlijst = new ArrayList<>();
        vragenlijst = vraagRepository.getAllVragenFEvaluatietemplate(TEMP_ID);
        //.forEach(stageopdrachten::add);

        return vragenlijst;
    }

    @Override
    public double getAantalPaginas() {
        double aantalRecords = (double) vraagRepository.count();
        return Math.ceil(aantalRecords / PAGESIZE);
    }

    @Override
    public List<Vraag> getAllVragen(int pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, PAGESIZE, Sort.Direction.ASC, "id");
        return vraagRepository.findAll(request).getContent();
    }

    @Override
    public Vraag getVraagByID(Integer vraagID){
        return vraagRepository.findOne(vraagID);
    }

}
