package be.ift.services.servicesimpl;

import be.ift.domain.School;
import be.ift.repositories.SchoolRepository;
import be.ift.services.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JHNBD42 on 8/03/2017.
 */
@Service
public class SchoolServiceImpl implements SchoolService {
    private final static int PAGESIZE = 10;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    public void setSchoolRepository(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public List<School> getAllScholen() {
        List<School> scholen = new ArrayList<>();
        schoolRepository.findAll()
                .forEach(scholen::add);

        return scholen;
    }

    @Override
    public School getOneSchool(Integer ID){
        return schoolRepository.findOne(ID);
    }

    @Override
    public List<School> getAllScholen(int pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, PAGESIZE, Sort.Direction.ASC, "id");
        return schoolRepository.findAll(request).getContent();
    }

    @Override
    public double getAantalPaginas() {
        double aantalRecords = (double) schoolRepository.count();
        return Math.ceil(aantalRecords / PAGESIZE);
    }
    @Override
    public School saveSchool(School school) {
        return schoolRepository.save(school);
    }

    @Override
    public School updateSchool(Integer id, School school) {return schoolRepository.save(school);}

    public double getAantalPaginasSearch(String naam) {

        double aantalRecords = (double) schoolRepository.countBySearch(makeWildcard(naam));
        return Math.ceil(aantalRecords / PAGESIZE);
    }

    public String makeWildcard(String naam){
        StringBuilder str = new StringBuilder(naam);
        str.insert(0,'%');
        str.insert(str.length(), '%');
        return str.toString();
    }

    @Override
    public List<School> getScholenBySearch(String naam, Integer queryOffset) {
        return schoolRepository.getSchoolBySearch(makeWildcard(naam), queryOffset);
    }
}

