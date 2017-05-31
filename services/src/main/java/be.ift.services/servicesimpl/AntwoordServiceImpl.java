package be.ift.services.servicesimpl;

import be.ift.domain.Antwoord;
import be.ift.repositories.AntwoordRepository;
import be.ift.services.AntwoordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by JHNBD42 on 18/04/2017.
 */
@Service
public class AntwoordServiceImpl implements AntwoordService {
    @Autowired
    public AntwoordRepository antwoordRepository;

    @Override
    public Antwoord saveAntwoord(Antwoord antwoord){
        return antwoordRepository.save(antwoord);
    }


}
