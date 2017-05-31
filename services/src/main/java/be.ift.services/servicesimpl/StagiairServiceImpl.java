package be.ift.services.servicesimpl;

import be.ift.domain.Begeleiding;
import be.ift.domain.Stage;
import be.ift.domain.Persoon;
import be.ift.domain.Stagiair;
import be.ift.repositories.StagiairRepository;
import be.ift.services.StagiairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JHNBD42 on 8/03/2017.
 */
@Service
public class StagiairServiceImpl implements StagiairService {

    private final static int PAGESIZE = 10;
    @Autowired
    private StagiairRepository stagiairRepository;

    @Override
    public List<Stagiair> getAllStagiaires() {
        List<Stagiair> stagiaires = new ArrayList<>();
        stagiairRepository.findAll()
                .forEach(stagiaires::add);
        return stagiaires;
    }

    @Override
    public List<Stagiair> getAllStagiairesFCategorie(Integer CAT_ID) {
        return stagiairRepository.findAllStagiairesFCategorie(CAT_ID);}

    @Override
    public List<Stagiair> getAllStagiairesFSchool(Integer SCHO_ID) {
        return stagiairRepository.findAllStagiairesFSchool(SCHO_ID);}

    @Override
    public List<Stagiair> getAllStagiairesFCategorieSchool(Integer CAT_ID, Integer SCHO_ID) {
        return stagiairRepository.findAllStagiairesFCategorieSchool(CAT_ID, SCHO_ID);
    }
    @Override
    public Stagiair getOneStagiair(int id){
        return stagiairRepository.findOne(id);
    }

    @Override
    public Stage getCurrentStage(Integer ID) {
        return stagiairRepository.findCurrentStageFromStagiair(ID);
    }

    @Override
    public List<Stage> getOldStages(Integer ID) {
        return stagiairRepository.findOldStageFromStagiair(ID);
    }

    @Override
    public Begeleiding getCurrentBegeleidingByStageID(Integer ID) {
        return stagiairRepository.findCurrentBegeleidingByStageID(ID);
    }
    @Override
    public Stagiair saveStagiair(Stagiair stagiair) {
        return stagiairRepository.save(stagiair);}

    @Override
    public List<Stagiair> getAllStagiaires(int pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, PAGESIZE, Sort.Direction.ASC, "id");
        return stagiairRepository.findAll(request).getContent();
    }

    @Override
    public double getAantalPaginas() {
        double aantalRecords = (double) stagiairRepository.count();
        return Math.ceil(aantalRecords / PAGESIZE);
    }

    @Override
    public List<Stagiair> getAllStagiairesFCategorie(Integer categorieId, Integer queryOffset) {

        List<Stagiair> stagiaires = stagiairRepository.findAllStagiairesFCategorie(categorieId, queryOffset);
        return stagiaires;
    }

    @Override
    public double getAantalPaginasFCategorie(Integer CAT_ID) {
        double aantalRecords = (double) stagiairRepository.countByCategorie(CAT_ID);
        return Math.ceil(aantalRecords / PAGESIZE);
    }

    @Override
    public int getAantalStagiairesBySchool(Integer SCHO_ID) {
        return stagiairRepository.countBySchool(SCHO_ID);
    }

    public int getAantalStagiairPerCat(Integer CAT_ID) {
        Integer aantalStag = stagiairRepository.countByCategorie(CAT_ID);
        return aantalStag;
    }

    @Override
    public double getAantalPaginasFSchool(Integer SCHO_ID) {
        double aantalRecords = (double) stagiairRepository.countBySchool(SCHO_ID);
        return Math.ceil(aantalRecords / PAGESIZE);
    }

    @Override
    public double getAantalPaginasFSchoolCategorie(Integer SCHO_ID, Integer CAT_ID) {
        double aantalRecords = (double) stagiairRepository.countBySchoolCategorie(SCHO_ID, CAT_ID);
        return Math.ceil(aantalRecords / PAGESIZE);
    }

    @Override
    public List<Stagiair> getAllStagiairesFSchool(Integer SCHO_ID, Integer queryOffset) {
        List<Stagiair> stagiaires = stagiairRepository.findAllStagiairesFSchool(SCHO_ID, queryOffset);
        return stagiaires;
    }

    @Override
    public List<Stagiair> getAllStagiairesFCategorieSchool(Integer SCHO_ID, Integer CAT_ID, Integer queryOffset) {
        List<Stagiair> stagiaires = stagiairRepository.findAllStagiairesFCategorieSchool(SCHO_ID, CAT_ID, queryOffset);
        return stagiaires;
    }

    /*Search*/
    @Override
    public double getAantalPaginasSearch(String naam) {
        double aantalRecords = (double) stagiairRepository.countBySearch(makeWildcard(naam));
        return Math.ceil(aantalRecords / PAGESIZE);
    }

    public String makeWildcard(String naam){
        StringBuilder str = new StringBuilder(naam);
        str.insert(0,'%');
        str.insert(str.length(), '%');
        return str.toString();
    }

    @Override
    public List<Stagiair> getStagiairesBySearch(String naam, Integer queryOffset) {
        return stagiairRepository.getStagiairBySearch(makeWildcard(naam), queryOffset);
    }

    @Override
    public List<Stagiair> getStagiairesByBegeleider(Integer BegId) {
        return stagiairRepository.getBegeleiddeStagiairs(BegId);
    }

    public double aantalStagiaires() {
        return stagiairRepository.count();

    }
}
