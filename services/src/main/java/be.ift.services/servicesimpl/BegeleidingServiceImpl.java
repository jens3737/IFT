package be.ift.services.servicesimpl;

import be.ift.domain.Begeleiding;
import be.ift.repositories.BegeleidingRepository;
import be.ift.services.BegeleidingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VXFBD43 on 20/03/2017.
 */
@Service
public class BegeleidingServiceImpl implements BegeleidingService {

    @Autowired
    BegeleidingRepository begeleidingRepository;



    @Override
    public Begeleiding saveBegeleiding(Begeleiding begeleiding) {
        return begeleidingRepository.save(begeleiding);
    }

    @Override
    public List<Begeleiding> BegeleidingFBegeleider(int id) {
        List<Begeleiding> BegeleidingFBegeleider = new ArrayList<Begeleiding>();
        begeleidingRepository.findAllBegeleidingFBegeleider(id).forEach(BegeleidingFBegeleider::add);

        return BegeleidingFBegeleider;
    }

}
