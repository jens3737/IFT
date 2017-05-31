package be.ift.services.servicesimpl;

import be.ift.domain.Persoon;
import be.ift.repositories.PersoonRepository;
import be.ift.services.PersoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by VXFBD43 on 14/03/2017.
 */

@Service
public class PersoonServiceImpl implements PersoonService {

    @Autowired
    private PersoonRepository persoonRepository;

    @Override
    public Persoon savePersoon(Persoon persoon) {
        return persoonRepository.save(persoon);
    }

    @Override
    public Persoon getOnePersoon(Integer id) {
        return persoonRepository.findOne(id);
    }



}
