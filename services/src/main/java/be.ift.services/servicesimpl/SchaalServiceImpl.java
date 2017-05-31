package be.ift.services.servicesimpl;

import be.ift.domain.Schaal;
import be.ift.repositories.SchaalRepository;
import be.ift.services.SchaalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JAHBD44 on 29/03/2017.
 */
@Service
public class SchaalServiceImpl implements SchaalService{

    @Autowired
    public SchaalRepository schaalRepository;


    @Override
    public List<Schaal> getAllSchalen() {
        List<Schaal> schaalLijst = new ArrayList<>();
        schaalRepository.findAll()
                .forEach(schaalLijst::add);
        return schaalLijst;
    }

    @Override
    public Schaal getSchaalByID(int schaalID){
        return schaalRepository.findOne(schaalID);
    }


}
